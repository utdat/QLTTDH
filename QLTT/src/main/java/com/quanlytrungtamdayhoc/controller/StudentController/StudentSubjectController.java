package com.quanlytrungtamdayhoc.controller.StudentController;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/student")
public class StudentSubjectController {

	@Autowired
	SubjectMapper subjectMapper;

	@Autowired
	TeacherMapper teacherMapper;

//	@GetMapping("/subject")
//	public String getSuject(Model model, Principal principal) {
//
//		Teacher teacher = new Teacher();
//		model.addAttribute("teachers", teacher);
//
//		List<Subject> list = subjectMapper.listSubject();
//		model.addAttribute("list_subject", list);
//		return "student/student_subject";
//
//	}

	@GetMapping("/subject/showTeacher/{id}")
	public String getInfTeacher(@PathVariable("id") int id, Model model) {

		Teacher teacher = teacherMapper.getTeacher(id);

		model.addAttribute("teachers", teacher);

		List<Subject> list = subjectMapper.listSubject();
		model.addAttribute("list_subject", list);

		return "student/student_subject";
	}

//	@GetMapping(value = { "**" })
//	public String home() {
//		return "redirect:/student/profile";
//	}

	@GetMapping("/subject")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("subjectlist", null);
		return "redirect:/student/subject/page/1";
	}

	@GetMapping("/subject/page/{pageNumber}")
	public String showEmployeePage(HttpServletRequest request,
			@PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("subjectlist");
		int pagesize = 2;
		
		Teacher teacher = new Teacher();
		model.addAttribute("teachers", teacher);
		
		List<Subject> list = subjectMapper.listSubject();
		
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("subjectlist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/student/subject/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("subjects", pages);
		return "/student/student_subject";
	}
}