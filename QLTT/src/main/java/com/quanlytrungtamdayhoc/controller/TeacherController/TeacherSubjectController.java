package com.quanlytrungtamdayhoc.controller.TeacherController;

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
import com.quanlytrungtamdayhoc.dbo.Student_Score;
import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentScoreMapper;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/teacher")
public class TeacherSubjectController {
	
	@Autowired
	private StudentScoreMapper studentScoreMapper;
	
	@Autowired
	private SubjectMapper subjectMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	private static final int PAGE_SIZE = 5;
	
	@GetMapping("/schedule")
	public String indexSubject(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("pageList", null);
		return "redirect:/teacher/schedule/page/1";
	}
	
	@GetMapping("/schedule/page/{pageNumber}")
	public String getSchedule(Model model, Principal principal,
							  HttpServletRequest request,
							  @PathVariable int pageNumber,
							  @RequestParam(name = "year", required = false) String year,
							  @RequestParam(name = "subName", required = false) String subName) {
		
		String baseUrl = "/teacher/schedule/page/";
		
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("pageList");
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		Teacher teacher = teacherMapper.getTeacher(0, current_account.getAccUsername());
		
		List<Subject> subjectList = subjectMapper.getAllSubject();
		List<Subject> teacherSubject = subjectMapper.getTeacherSubject(teacher.getTeaId(), year, subName);
		
		if (pages == null || year != null || subName != null) {
			pages = new PagedListHolder<>(teacherSubject);
			pages.setPageSize(PAGE_SIZE);
		} else {
			
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - teacherSubject.size());
		int end = Math.min(begin + 5, pages.getPageCount()); 
		int totalPageCount = pages.getPageCount();
		
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("pagedListHolder", pages);
		
		request.getSession().setAttribute("pageList", pages);
		
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("teacher", teacher);

		return "teacher/TeacherSchedule";
	}
	
	@GetMapping("/mark")
	public String indexMark(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("pageList", null);
		return "redirect:/teacher/mark/page/1";
	}
	
	@GetMapping("/mark/page/{pageNumber}")
	public String markScoreAll(Model model, Principal principal,
							   HttpServletRequest request,
							   @PathVariable int pageNumber) {
		
		String baseUrl = "/teacher/mark/page/";
		
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("pageList");
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		Teacher teacher = teacherMapper.getTeacher(0, current_account.getAccUsername());
		
		List<Student_Score> studentScore = studentScoreMapper.getTeacherMark(teacher.getTeaId(), 0);
		
		if (pages == null) {
			pages = new PagedListHolder<>(studentScore);
			pages.setPageSize(PAGE_SIZE);
		} else {
			
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		
		request.getSession().setAttribute("pageList", pages);
		
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - studentScore.size());
		int end = Math.min(begin + 5, pages.getPageCount()); 
		int totalPageCount = pages.getPageCount();
		
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("pagedListHolder", pages);
		
		model.addAttribute("teacher", teacher);

		return "teacher/TeacherMarkAll";
	}
	
	@GetMapping("/mark/{subId}")
	public String markScore(Model model, Principal principal, @PathVariable int subId) {
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		Teacher teacher = teacherMapper.getTeacher(0, current_account.getAccUsername());
		
		List<Student_Score> studentScore = studentScoreMapper.getTeacherMark(teacher.getTeaId(), subId);
		
		model.addAttribute("studentScore", studentScore);
		model.addAttribute("teacher", teacher);

		return "teacher/TeacherMark";
	}
	
	@GetMapping("/mark/update")
	public String updateScore(Model model, Principal principal,
							  RedirectAttributes redirectAttributes,
							  @RequestParam(name = "subId") int subId,
							  @RequestParam(name = "stuId") int stuId,
							  @RequestParam(name = "score") float score) {
		
		String message = null;
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		Teacher teacher = teacherMapper.getTeacher(0, current_account.getAccUsername());
		
		List<Student_Score> studentScore = studentScoreMapper.getTeacherMark(teacher.getTeaId(), subId);
		
		
		if(score < 0 || score > 10) {
			model.addAttribute("message", "Invalid score");
			model.addAttribute("studentScore", studentScore);
			model.addAttribute("teacher", teacher);
			return "teacher/TeacherMark";
		}
		
		if(studentScoreMapper.updateScore(subId, stuId, score) > 0) {
			message = "Update score successfully";
		}else{
			message = "Update score failed";
		}
		
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/teacher/mark/" + subId;
	}
}
