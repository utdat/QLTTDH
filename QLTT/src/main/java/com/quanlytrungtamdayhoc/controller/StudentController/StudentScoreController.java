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
import com.quanlytrungtamdayhoc.dbo.Student_Score;
import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.StudentScoreMapper;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;

@Controller
@RequestMapping("/student")
public class StudentScoreController {

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	private SubjectMapper subjectMapper;

	@Autowired
	StudentScoreMapper studentScoreMapper;
	
	private static final int PAGE_SIZE = 5;

	@GetMapping("/score")
	public String index(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("pageList", null);
		
		return "redirect:/student/score/page/1";
	}
	
	@GetMapping("/score/page/{pageNumber}")
	public String showSubjectStudent(HttpServletRequest request, 
								   @PathVariable int pageNumber,
								   Principal principal, Model model,
								   @RequestParam(name = "year", required = false) String year,
								   @RequestParam(name = "subName", required = false) String subName) {
		
		String baseUrl = "/student/score/page/";
		String message = (String) request.getSession().getAttribute("mess");
		
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("pageList");
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();

		Student student = studentMapper.getStudent(0, currentAccount.getAccUsername());
		List<Subject> subjectList = subjectMapper.getAllSubject(null, null);
		List<Student_Score> studentScore = studentScoreMapper.getStudentScore(student.getStuId(), subName, year);
		
		if (pages == null || year != null || subName != null) {
			pages = new PagedListHolder<>(studentScore);
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
		int begin = Math.max(1, current - studentScore.size());
		int end = Math.min(begin + 5, pages.getPageCount()); 
		int totalPageCount = pages.getPageCount();
		
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("pagedListHolder", pages);
		
		model.addAttribute("student", student);
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("message", message);
		
		return "student/StudentScore";
	}

	@GetMapping("/score/delete")
	public String deleteStudentSubject(Model model, Principal principal,
									   HttpServletRequest request,
									   @RequestParam(name = "subId") int subId) {
		
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentMapper.getStudent(0, currentAccount.getAccUsername());
		String message = null;
		
		if (studentScoreMapper.deleteStudentSubject(subId, student.getStuId()) > 0) {
			message = "Successfully canceled subject registration";
		}else {
			message = "Failed to cancel subject registration";
		}
		
		request.getSession().setAttribute("mess", message);
		return "redirect:/student/score";
	}
}
