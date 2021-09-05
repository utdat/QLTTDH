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

import com.quanlytrungtamdayhoc.dbo.Student_Score;
import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentScoreMapper;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/admin")
public class AdminSubjectController {

	@Autowired
	SubjectMapper subjectMapper;

	@Autowired
	TeacherMapper teacherMapper;
	
	@Autowired
	StudentScoreMapper studentScoreMapper;

	private final static int PAGE_SIZE = 5;

	// phân trang
	@GetMapping("/subject")
	public String index(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("pageList", null);
		return "redirect:/admin/subject/page/1";
	}

	@GetMapping("/subject/page/{pageNumber}")
	public String getAdminSubject(Model model, HttpServletRequest request, 
								  @PathVariable int pageNumber,
								  @RequestParam(name = "year", required = false) String year,
								  @RequestParam(name = "teaName", required = false) String teaName) {

		String baseUrl = "/admin/subject/page/";
		String message = (String) request.getSession().getAttribute("mess");
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("pageList");

		List<Teacher> teacherList = teacherMapper.getAllTeacher();
		List<Subject> subjectList = subjectMapper.getAllSubject(teaName, year);

		if (pages == null || year != null || teaName != null) {
			pages = new PagedListHolder<>(subjectList);
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
		int begin = Math.max(1, current - subjectList.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("pagedListHolder", pages);
		
		model.addAttribute("message", message);
		model.addAttribute("teacherList", teacherList);

		return "admin/Subject";
	}

	@PostMapping("/subject")
	public String insertSubject(Model model, HttpServletRequest request,
								@RequestParam(name = "name") String subName,
								@RequestParam(name = "startdate") String subStartdate,
								@RequestParam(name = "schedule") String subSchedule,
								@RequestParam(name = "room") String subRoom, 
								@RequestParam(name = "tuition") int subTuition,
								@RequestParam(name = "teaId", required = false) Integer teaId) {

		String message = null;
		
		if(teaId == null) {
			message = "Please choose a teacher";
		}else {
			if (subjectMapper.insertSubject(subName, subStartdate, subSchedule, subRoom,subTuition, teaId) > 0) { 
				message = "Successfully add subject"; 
			} else {
				message = "Add subject failed"; 
			} 
		}
		
		request.getSession().setAttribute("mess", message);

		return "redirect:/admin/subject";
	}
	
	@GetMapping("/subject/{subId}")
	public String getAdminSubjectDetailById(@PathVariable int subId, Model model) {

		Subject subject = subjectMapper.getSubject(subId);
		Teacher teacher = teacherMapper.getTeacher(subject.getTeacher().getTeaId(), null);
		List<Teacher> teacherList = teacherMapper.getAllTeacher();
		
		model.addAttribute("teacherList", teacherList);
		model.addAttribute("teacher", teacher);
		model.addAttribute("subject", subject);

		return "admin/SubjectDetail";
	}
	
	@PostMapping("/subject/{subId}")
	public String updateSubject(Model model, 
								@PathVariable int subId,
								@ModelAttribute(value = "subject") Subject subject, 
								@RequestParam(name = "teaId") int teaId,
								@RequestParam(name = "startdate") String startdate) {

		String message = null;
		
		if (subjectMapper.updateSubject(subject, teaId, startdate) > 0) {
			message = "Successfully update subject";
		}
		else {
			message = "Update subject failed";
		}
		
		subject = subjectMapper.getSubject(subId);
		Teacher teacher = teacherMapper.getTeacher(subject.getTeacher().getTeaId(), null);
		List<Teacher> teacherList = teacherMapper.getAllTeacher();

		model.addAttribute("message", message);
		model.addAttribute("teacher", teacher);
		model.addAttribute("teacherList", teacherList);
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
	
	@GetMapping("/subject/delete")
	public String deleteSubject(Model model, HttpServletRequest request,
								@RequestParam(name = "subId") int subId) {
		String message = null;
		List<Student_Score> scoreList = studentScoreMapper.getScoreBySubject(subId);
		
		if(scoreList.size() > 0) {
			message = scoreList.size() + " students have registered for this subject\nCan not delete this subject";
		}else {
			if (subjectMapper.deleteSubject(subId) > 0) {
				message = "Delete subject successfully";
			} else {
				message = "Delete subject failed";
			}
		}
		
		request.getSession().setAttribute("mess", message);

		return "redirect:/admin/subject";
	}
	
}
