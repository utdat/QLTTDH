package com.quanlytrungtamdayhoc.controller.AdminController;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/admin")
public class AdminSubjectController {

	@Autowired
	SubjectMapper subjectMapper;

	@Autowired
	TeacherMapper teacherMapper;

	private final static int PAGE_SIZE = 5;

	// phân trang
	@GetMapping("/subject")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("subjectlist", null);
		return "redirect:/admin/subject/page/1";
	}

	@GetMapping("/subject/page/{pageNumber}")
	public String getAdminSubject(Model model, HttpServletRequest request, @PathVariable int pageNumber,
			Principal principal) {

		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("subjectlist");

		List<Teacher> teacher = teacherMapper.getAllTeacher();
		model.addAttribute("teacherList", teacher);

		List<Subject> list = subjectMapper.getListSubject();

		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(PAGE_SIZE);
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
		String baseUrl = "/admin/subject/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("subjects", pages);

		return "admin/Subject";
	}

	@PostMapping("/subject")
	public String insertSubject(Model model, @RequestParam(name = "name") String subName,
			@RequestParam(name = "startdate") String subStartdate, @RequestParam(name = "schedule") String subSchedule,
			@RequestParam(name = "room") String subRoom, @RequestParam(name = "tuition") int subTuition,
			@RequestParam(name = "teaId") int teaId) {

		String message = null;

		if (subjectMapper.insertSubject(subName, subStartdate, subSchedule, subRoom, subTuition, teaId) > 0) {
			message = "Add Subject Success";
		}

		List<Subject> list = subjectMapper.getListSubject();
		model.addAttribute("listSubject", list);
		model.addAttribute("message", message);

		return "redirect:/admin/subject";
	}

	@PostMapping("/subject/detail/update/{subId}")
	public String updateSubject(Model model, @PathVariable int subId,
			@ModelAttribute(value = "subject") Subject subject, @RequestParam(name = "teaId") int teaId,
			@RequestParam(name = "startdate") String startdate) {

		String message = null;
		System.out.println(teaId);
		if (subjectMapper.updateSubject(subject, teaId, startdate) > 0) {
			message = "Update Subject Success";
			return "redirect:/admin/subject/detail/" + subId;
		}
		Teacher teacher = new Teacher();

		model.addAttribute("message", message);
		model.addAttribute("teacher", teacher);
		model.addAttribute("subject", subject);

		return "admin/SubjectDetail";
	}

	@GetMapping("/subject/delete")
	public String deleteSubject(Model model, @RequestParam(name = "subId") int subId) {
		String message = null;

		if (subjectMapper.deleteSubject(subId) > 0) {
			message = "Delete Subject Success";
		} else {
			message = "Delete Subject Fail";
		}

		List<Subject> list = subjectMapper.getListSubject();

		model.addAttribute("listSubject", list);
		model.addAttribute("message", message);

		return "redirect:/admin/subject";
	}

	@GetMapping("/subject/detail/{subId}")
	public String getAdminSubjectDetailById(@PathVariable int subId, Model model) {

		Subject subject = subjectMapper.getSubjectById(subId);
		int teaId = subject.getTeacher().getTeaId();

		List<Teacher> list = teacherMapper.getAllTeacher();
		Teacher teacher = teacherMapper.getTeacher(teaId, null);

		model.addAttribute("teacherList", list);
		model.addAttribute("teacher", teacher);
		model.addAttribute("subject", subject);

		return "admin/SubjectDetail";
	}

	@GetMapping("/subject/detail")
	public String getAdminSubjectDetail(Model model) {
		Subject subject = new Subject();
		Teacher teacher = new Teacher();

		model.addAttribute("teacher", teacher);
		model.addAttribute("subject", subject);
		return "admin/SubjectDetail";
	}
}