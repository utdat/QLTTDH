package com.quanlytrungtamdayhoc.controller.StudentController;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.StudentScoreMapper;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/student")
public class StudentSubjectController {

	@Autowired
	private SubjectMapper subjectMapper;

	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;

	@Autowired
	private StudentScoreMapper studentScoreMapper;
	
	private static final int PAGE_SIZE = 5;

	@GetMapping("/subject")
	public String index(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("pageList", null);
		return "redirect:/student/subject/page/1";
	}

	// phân trang
	@GetMapping("/subject/page/{pageNumber}")
	public String showSubjectStudent(HttpServletRequest request, 
								   @PathVariable int pageNumber,
								   Principal principal, Model model,
								   @RequestParam(name = "teaName", required = false) String teaName,
								   @RequestParam(name = "subName", required = false) String subName) {
		
		String baseUrl = "/student/subject/page/";
		String message = (String) request.getSession().getAttribute("mess");
		
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("pageList");
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentMapper.getStudent(0, currentAccount.getAccUsername());
		
		List<Teacher> teacherList = teacherMapper.getAllTeacher();
		List<Subject> subjectList = subjectMapper.getAllSubject();
		List<Subject> studentSubject = subjectMapper.listStudentSubject(student.getStuId(), teaName, subName);
		
		if (pages == null || teaName != null || subName != null) {
			pages = new PagedListHolder<>(studentSubject);
			pages.setPageSize(PAGE_SIZE);
		} else {
			
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		
		request.getSession().setAttribute("pageList", pages);
		request.getSession().removeAttribute("mess");
		
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - studentSubject.size());
		int end = Math.min(begin + 5, pages.getPageCount()); 
		int totalPageCount = pages.getPageCount();
		
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("pagedListHolder", pages);
		
		model.addAttribute("student", student);
		model.addAttribute("teacherList", teacherList);
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("message", message);
		
		return "student/StudentSubject";
	}

	@GetMapping("/subject/insert")
	public String insertSubject(Model model, Principal principal,
								HttpServletRequest request, 
								@RequestParam(name = "subId", required = false) Integer subId) {

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentMapper.getStudent(0, currentAccount.getAccUsername());
		String message = null;

		// insert
		if (studentScoreMapper.insertSubjectScore(student.getStuId(), subId) > 0) {
			message = "Successfully registered for the subject";
		} else {
			message = "Failed to register for the subject";
		}
		
		request.getSession().setAttribute("mess", message);
		return "redirect:/student/subject";
	}

}
