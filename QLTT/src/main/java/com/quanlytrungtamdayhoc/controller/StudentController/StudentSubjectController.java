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
	SubjectMapper subjectMapper;

	@Autowired
	TeacherMapper teacherMapper;

	@Autowired
	StudentMapper studentMapper;

	@Autowired
	StudentScoreMapper studentScoreMapper;

	@GetMapping("/subject/showTeacher/{id}")
	public String getInfTeacher(@PathVariable("id") int id, Model model, HttpServletRequest request,
			Principal principal) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("subjectlist");

		int pagesize = 2;

		Teacher teacher = teacherMapper.getTeacherById(id);
		model.addAttribute("teachers", teacher);

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentMapper.getStudentByEmail(currentAccount.getAccUsername());

		List<Subject> list = subjectMapper.listSubject(student.getStuId());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = 0;
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
		model.addAttribute("list_subject", list);
		model.addAttribute("subjects", pages);
		return "student/student_subject";
	}

	@GetMapping("/subject")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("subjectlist", null);
		return "redirect:/student/subject/page/1";
	}

	// phân trang
	@GetMapping("/subject/page/{pageNumber}")
	public String showEmployeePage(HttpServletRequest request, @PathVariable int pageNumber, Model model,
			Principal principal) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("subjectlist");
		int pagesize = 2;

		Teacher teacher = new Teacher();
		model.addAttribute("teachers", teacher);

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentMapper.getStudentByEmail(currentAccount.getAccUsername());
		int stuId = student.getStuId();

		List<Subject> list = subjectMapper.listSubject(stuId);

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
		return "student/student_subject";
	}

	@GetMapping("/subject/insert")
	public String insertSubject(RedirectAttributes redirect, HttpServletRequest request, Model model,
			@RequestParam(name = "subId") int subId, Principal principal) {

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentMapper.getStudentByEmail(currentAccount.getAccUsername());
		int stuId = student.getStuId();

		// mã giả
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("subjectlist");
		int pagesize = 2;
		Teacher teacher = teacherMapper.getTeacherById(stuId);
		model.addAttribute("teachers", teacher);

		List<Subject> list = subjectMapper.listSubject(0);
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = 0;
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
		model.addAttribute("list_subject", list);
		model.addAttribute("subjects", pages);

		// insert
		if (studentScoreMapper.insertSubjectScore(stuId, subId, 0) > 0) {
			return "redirect:/student/subject";

		} else {
			System.out.println("error");
		}
		return "student/student_subject";
	}

}