package com.quanlytrungtamdayhoc.controller.AdminController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.AccountMapper;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final int PAGE_SIZE = 5;
	
	@GetMapping("/student")
	public String indexAccountStudent(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("studentPageList", null);
		request.getSession().setAttribute("accountPageList", null);
		
		return "redirect:/admin/account/student/page/1";
	}
	
	@GetMapping("/student/page/{pageNumber}")
	public String getAccountStudent(Model model,
									HttpServletRequest request,
									@PathVariable int pageNumber,
									@RequestParam(name = "stuName", required = false) String stuName,
									@RequestParam(name = "stuPhone", required = false) String stuPhone) {
		
		String baseUrl = "/admin/account/student/page/";
		String message = (String) request.getSession().getAttribute("mess");
		
		PagedListHolder<?> studentPages = (PagedListHolder<?>) request.getSession().getAttribute("studentPageList");
		PagedListHolder<?> accountPages = (PagedListHolder<?>) request.getSession().getAttribute("accountPageList");
		
		List<Student> nameList = studentMapper.getAllStudent();
		List<Student> studentList = studentMapper.getStudentByFilter(stuName, stuPhone);
		List<Account> accountList = accountMapper.getAccountByRole(1, stuName, stuPhone); 
		
		if (studentPages == null || stuName != null || stuPhone != null) {
			studentPages = new PagedListHolder<>(studentList);
			accountPages = new PagedListHolder<>(accountList);
			
			studentPages.setPageSize(PAGE_SIZE);
			accountPages.setPageSize(PAGE_SIZE);
		} else {
			
			final int goToPage = pageNumber - 1;
			if (goToPage <= studentPages.getPageCount() && goToPage >= 0) {
				studentPages.setPage(goToPage);
				accountPages.setPage(goToPage);
			}
		}
		
		request.getSession().setAttribute("studentPageList", studentPages);
		request.getSession().setAttribute("accountPageList", accountPages);
		request.getSession().removeAttribute("mess");
		
		int current = studentPages.getPage() + 1;
		int begin = Math.max(1, current - studentList.size());
		int end = Math.min(begin + 5, studentPages.getPageCount()); 
		int totalPageCount = studentPages.getPageCount();
		
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("studentPagedListHolder", studentPages);
		model.addAttribute("accountPagedListHolder", accountPages);
		
		model.addAttribute("nameList", nameList);
		model.addAttribute("message", message);
		
		return "admin/AccountStudent";
	}
	
	@PostMapping("/student")
	public String addAccountStudent(Model model, HttpServletRequest request,
									@RequestParam(name = "name") String stuName,
									@RequestParam(name = "phone") String stuPhone,
									@RequestParam(name = "school") String stuSchool,
									@RequestParam(name = "email") String stuEmail,
									@RequestParam(name = "password") String stuPassword) {
		
		String message = null;
		Account account = accountMapper.getAccount(stuEmail);
		
		if(account != null) {
			message = "Email already exists";
		}else {
			if(accountMapper.addAccount(stuEmail, passwordEncoder.encode(stuPassword), 1) > 0) {
				message = "Successfully add account";
				
				if(studentMapper.addStudent(stuName, stuPhone, stuSchool, stuEmail) > 0) {
					message += "\nSuccessfully add student";
				}else {
					message += "\nAdd student failed";
					
					if (accountMapper.deleteAccount(stuEmail) > 0) {
						message += "\nSuccessfully rollback add account";
					}else {
						message += "\nRollback add account failed";
					}
				}
			}
			else {
				message = "Add account failed";
			}
		}
		
		request.getSession().setAttribute("mess", message);
		
		return "redirect:/admin/account/student";
	}
	
	@GetMapping("/student/{stuId}")
	public String getStudentDetail(Model model, @PathVariable int stuId) {
		
		Student student = studentMapper.getStudent(stuId, null);
		model.addAttribute("student", student);
		
		return "admin/StudentDetail";
	}
	
	@PostMapping("/student/{stuId}")
	public String updateStudentDetail(Model model, @PathVariable int stuId,
									 @ModelAttribute(value="student") Student student,
									 @RequestParam(name = "birthdate") String birthdate,
									 @RequestParam(name = "confirmPass") String confirmPass,
									 @RequestParam(name = "newPass") String newPass) {
		
		String message = null;
		
		if(studentMapper.updateStudent(student, birthdate) > 0) {
			message = "Successfully update information";
			
			if(!newPass.equals("")) {
				if(newPass.equals(confirmPass) && accountMapper.updatePassword(student.getStuEmail(), passwordEncoder.encode(newPass)) > 0) {
					message = "Successfully update password";
				}else {
					message = "Update password failed";
				}
			}
		}else {
			message = "Update information failed";
		}
		
		student = studentMapper.getStudent(stuId, null);
		
		model.addAttribute("student", student);
		model.addAttribute("message", message);
		return "admin/StudentDetail";
	}
	
	@GetMapping("/teacher")
	public String indexAccountTeacher(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("teacherPageList", null);
		request.getSession().setAttribute("accountPageList", null);
		
		return "redirect:/admin/account/teacher/page/1";
	}
	
	@GetMapping("/teacher/page/{pageNumber}")
	public String getAccountTeacher(Model model, HttpServletRequest request,
									@PathVariable int pageNumber,
									@RequestParam(name = "teaName", required = false) String teaName,
									@RequestParam(name = "teaPhone", required = false) String teaPhone) {
		
		String baseUrl = "/admin/account/teacher/page/";
		String message = (String) request.getSession().getAttribute("mess");
		
		PagedListHolder<?> teacherPages = (PagedListHolder<?>) request.getSession().getAttribute("teacherPageList");
		PagedListHolder<?> accountPages = (PagedListHolder<?>) request.getSession().getAttribute("accountPageList");
		
		List<Teacher> nameList = teacherMapper.getAllTeacher();
		List<Teacher> teacherList = teacherMapper.getTeacherByFilter(teaName, teaPhone);
		List<Account> accountList = accountMapper.getAccountByRole(2, teaName, teaPhone); 
		
		if (teacherPages == null || teaName != null || teaPhone != null) {
			teacherPages = new PagedListHolder<>(teacherList);
			accountPages = new PagedListHolder<>(accountList);
			
			teacherPages.setPageSize(PAGE_SIZE);
			accountPages.setPageSize(PAGE_SIZE);
		} else {
			
			final int goToPage = pageNumber - 1;
			if (goToPage <= teacherPages.getPageCount() && goToPage >= 0) {
				teacherPages.setPage(goToPage);
				accountPages.setPage(goToPage);
			}
		}
		
		request.getSession().setAttribute("teacherPageList", teacherPages);
		request.getSession().setAttribute("accountPageList", accountPages);
		request.getSession().removeAttribute("mess");
		
		int current = teacherPages.getPage() + 1;
		int begin = Math.max(1, current - teacherList.size());
		int end = Math.min(begin + 5, teacherPages.getPageCount()); 
		int totalPageCount = teacherPages.getPageCount();
		
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("teacherPagedListHolder", teacherPages);
		model.addAttribute("accountPagedListHolder", accountPages);
		
		model.addAttribute("nameList", nameList);
		model.addAttribute("message", message);
		
		return "admin/AccountTeacher";
	}
	
	@PostMapping("/teacher")
	public String addAccountTeacher(Model model, HttpServletRequest request,
									@RequestParam(name = "name") String teaName,
									@RequestParam(name = "phone") String teaPhone,
									@RequestParam(name = "school") String teaSchool,
									@RequestParam(name = "salaryrate") String teaSalaryrate,
									@RequestParam(name = "email") String teaEmail,
									@RequestParam(name = "password") String teaPassword) {
		
		String message = null;
		Account account = accountMapper.getAccount(teaEmail);
		
		if(account != null) {
			message = "Email already exists";
		}else {
			if(accountMapper.addAccount(teaEmail, passwordEncoder.encode(teaPassword), 2) > 0) {
				message = "Successfully add account";
				
				if(teacherMapper.addTeacher(teaName, teaPhone, teaSchool, Float.parseFloat(teaSalaryrate), teaEmail) > 0) {
					message += "\nSuccessfully add teacher";
				}else {
					message += "\nAdd teacher failed";
					
					if (accountMapper.deleteAccount(teaEmail) > 0) {
						message += "\nSuccessfully rollback add account";
					}else {
						message += "\nRollback add account failed";
					}
				}
			}
			else {
				message = "Add account failed";
			}
		}
		
		request.getSession().setAttribute("mess", message);
		
		return "redirect:/admin/account/teacher";
	}
	
	@GetMapping("/teacher/{teaId}")
	public String getTeacherDetail(Model model, @PathVariable int teaId) {
		
		Teacher teacher = teacherMapper.getTeacher(teaId, null);
		model.addAttribute("teacher", teacher);
		
		return "admin/TeacherDetail";
	}
	
	@PostMapping("/teacher/{teaId}")
	public String updateTeacherDetail(Model model, @PathVariable int teaId,
									 @ModelAttribute(value="teacher") Teacher teacher,
									 @RequestParam(name = "birthdate") String birthdate,
									 @RequestParam(name = "confirmPass") String confirmPass,
									 @RequestParam(name = "newPass") String newPass) {
		
		String message = null;
		
		if(teacherMapper.updateTeacher(teacher, birthdate) > 0) {
			message = "Successfully update information";
			
			if(!newPass.equals("")) {
				if(newPass.equals(confirmPass) && accountMapper.updatePassword(teacher.getTeaEmail(), passwordEncoder.encode(newPass)) > 0) {
					message = "Successfully update password";
				}else {
					message = "Update password failed";
				}
			}
		}else {
			message = "Update information failed";
		}
		
		teacher = teacherMapper.getTeacher(teaId, null);
		
		model.addAttribute("teacher", teacher);
		model.addAttribute("message", message);
		return "admin/TeacherDetail";
	}
	
	@GetMapping("/detail")
	public String getAccountDetail(Model model) {
		
		Student student = new Student();
		model.addAttribute("student", student);
		
		return "admin/StudentDetail";
	}
	
	@GetMapping("/active")
	public String activateAccount(Model model, HttpServletRequest request,
								  @RequestParam(name = "username") String username) {
		
		String view = "redirect:/admin/account";
		String message = null;
		Account account = accountMapper.getAccount(username);
		
		if(account.getAccIsactive().equals("T")) {
			if(accountMapper.activateAccount(username, "F") > 0) {
				message = "Deactivate account successfully";
			}else {
				message = "Deactivate account failed";
			}
		}else {
			if(accountMapper.activateAccount(username, "T") > 0) {
				message = "Activate account successfully";
			}else {
				message = "Activate account failed";
			}
		}
		
		if(account.getAccRole() == 1) {
			view += "/student";
		}else {
			view += "/teacher";
		}
		
		request.getSession().setAttribute("mess", message);
		
		return view;
	}
}
