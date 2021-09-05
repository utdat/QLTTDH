package com.quanlytrungtamdayhoc.controller.AdminController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Student_Score;
import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.StudentScoreMapper;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;

@Controller
@RequestMapping("/admin/student")
public class AdminStudentSubjectController {

	@Autowired
	StudentScoreMapper studentScoreMapper;

	@Autowired
	StudentMapper studentMapper;

	@Autowired
	SubjectMapper subjectMapper;

	private final static int PAGE_SIZE = 5;

	// phân trang
	@GetMapping("/subject")
	public String index(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("pageList", null);
		return "redirect:/admin/student/subject/page/1";
	}

	@GetMapping("/subject/page/{pageNumber}")
	public String getSubject(Model model, HttpServletRequest request, 
							 @PathVariable int pageNumber,
							 @RequestParam(name = "stuName", required = false) String stuName,
							 @RequestParam(name = "subName", required = false) String subName) {

		String baseUrl = "/admin/student/subject/page/";
		String message = (String) request.getSession().getAttribute("mess");
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("pageList");

		List<Student> studentList = studentMapper.getAllStudent();
		List<Subject> subjectList = subjectMapper.getAllSubject(null, null);
		List<Student_Score> studentScoreList = studentScoreMapper.getAllStudentScore(stuName, subName);

		if (pages == null || stuName != null || subName != null) {
			pages = new PagedListHolder<>(studentScoreList);
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
		int begin = Math.max(1, current - studentScoreList.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("pagedListHolder", pages);

		model.addAttribute("message", message);
		model.addAttribute("studentList", studentList);
		model.addAttribute("subjectList", subjectList);

		return "admin/StudentSubject";
	}
	
	@PostMapping("/subject")
	public String insertSubject(Model model, HttpServletRequest request,
								@RequestParam(name = "stuId", required = false) Integer stuId,
								@RequestParam(name = "subId", required = false) Integer subId) {
		
		String message = null;
		
		if(stuId == null) {
			message = "Please choose a student";
		}else if(subId == null) {
			message = "Please choose a subject";
		}else {
			Student_Score studentScore = studentScoreMapper.getStudentScoreById(stuId, subId);
			
			if (studentScore != null) {
				message = "Students registered for this subject";
			} else {
				if (studentScoreMapper.insertSubjectScore(stuId, subId) > 0) {
					message = "Successfully registered for a student's subject";
				}else {
					message = "Subject registration for student failed";
				}
			}
		}
		
		request.getSession().setAttribute("mess", message);
		
		return "redirect:/admin/student/subject";
	}
	
	@GetMapping("/subject/delete")
	public String deleteSubject(Model model, HttpServletRequest request,
								@RequestParam(name = "subId") int subId,
								@RequestParam(name = "stuId") int stuId) {

		String message = null;
		
		if (studentScoreMapper.deleteStudentSubject(subId, stuId) > 0) {
			message = "Successfully canceled subject registration for student";
		} else {
			message = "Failed to cancel subject registration for student";
		}

		request.getSession().setAttribute("mess", message);
		
		return "redirect:/admin/student/subject";
	}

}
