package com.quanlytrungtamdayhoc.controller.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/student")
	public String getAccountStudent(Model model) {
		
		List<Student> studentList = studentMapper.getAllStudent();
		List<Account> accountList = accountMapper.getAccountByRole(1); 
		
		model.addAttribute("studentList", studentList);
		model.addAttribute("accountList", accountList);
		
		return "admin/AccountStudent";
	}
	
	@PostMapping("/student")
	public String addAccountStudent(Model model,
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
		
		List<Student> studentList = studentMapper.getAllStudent();
		model.addAttribute("studentList", studentList);
		model.addAttribute("message", message);
		
		return "admin/AccountStudent";
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
	public String getAccountTeacher(Model model) {
		
		List<Teacher> teacherList = teacherMapper.getAllTeacher();
		List<Account> accountList = accountMapper.getAccountByRole(2);
		
		model.addAttribute("teacherList", teacherList);
		model.addAttribute("accountList", accountList);
		
		return "admin/AccountTeacher";
	}
	
	@PostMapping("/teacher")
	public String addAccountTeacher(Model model,
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
		
		List<Teacher> teacherList = teacherMapper.getAllTeacher();
		model.addAttribute("teacherList", teacherList);
		model.addAttribute("message", message);
		
		return "admin/AccountTeacher";
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
	public String activateAccount(Model model, RedirectAttributes redirectAttributes,
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
		
		redirectAttributes.addFlashAttribute("message", message);
		
		return view;
	}
}
