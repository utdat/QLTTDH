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
		request.getSession().setAttribute("studentsubjectlist", null);
		return "redirect:/admin/student/subject/page/1";
	}

	@GetMapping("/subject/page/{pageNumber}")
	public String getSubject(Model model, HttpServletRequest request, @PathVariable int pageNumber) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("studentsubjectlist");

		List<Student_Score> listStudentScore = studentScoreMapper.getListStudentScore();
		List<Student> listStudent = studentMapper.getAllStudent();
		List<Subject> listSubject = subjectMapper.getListSubject();

		if (pages == null) {
			pages = new PagedListHolder<>(listStudentScore);
			pages.setPageSize(PAGE_SIZE);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}

		request.getSession().setAttribute("studentsubjectlist", pages);

		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - listStudentScore.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/admin/student/subject/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("listStudentSubject", pages);

		model.addAttribute("listStudent", listStudent);
		model.addAttribute("listSubject", listSubject);

		return "admin/StudentSubject";
	}

	@PostMapping("/subject")
	public String insertSubject(Model model, @RequestParam(name = "stuId") int stuId,
			@RequestParam(name = "subId") int subId) {
		String message = null;
		Student_Score studentScore = studentScoreMapper.getStudentScoreById(stuId, subId);
		if (studentScore != null) {
			message = "This student's subject already exists ";
		} else {
			if (studentScoreMapper.insertSubjectScore(stuId, subId) > 0) {
				message = "Add Student Subject Success";
			}
		}
		List<Student_Score> listStudentScore = studentScoreMapper.getListStudentScore();
		List<Student> listStudent = studentMapper.getAllStudent();
		List<Subject> listSubject = subjectMapper.getListSubject();

		model.addAttribute("listStudent", listStudent);
		model.addAttribute("listSubject", listSubject);
		model.addAttribute("listStudentSubject", listStudentScore);
		model.addAttribute("message", message);
		return "redirect:/admin/student/subject";
	}

	@GetMapping("/subject/delete")
	public String deleteSubject(Model model, @RequestParam(name = "subId") int subId,
			@RequestParam(name = "stuId") int stuId) {

		String message = null;
		if (studentScoreMapper.deleteStudentSubject(subId, stuId) > 0) {
			message = "Delete Student Subject Success";
		} else {
			message = "Delete Student Subject Fail";
		}

		List<Student_Score> listStudentScore = studentScoreMapper.getListStudentScore();
		List<Student> listStudent = studentMapper.getAllStudent();
		List<Subject> listSubject = subjectMapper.getListSubject();

		model.addAttribute("listStudent", listStudent);
		model.addAttribute("listSubject", listSubject);
		model.addAttribute("listStudentSubject", listStudentScore);
		model.addAttribute("message", message);
		return "redirect:/admin/student/subject";
	}

//	@PostMapping("/subject/detail")
//	public String updateStudentSubject(Model model, @ModelAttribute("studentSubject") Student_Score studentSubject,
//			@RequestParam(name = "subId") int subId, @RequestParam(name = "stuId") int stuId,
//			@RequestParam(name = "subjectId", required = false) int subjectId) {
//		Student_Score studentScore = studentScoreMapper.getStudentScoreById(stuId, subId);
//		Student student = studentMapper.getStudent(stuId, null);
//		Subject subject = subjectMapper.getSubjectById(subjectId);
//
//		List<Subject> listSubject = subjectMapper.getListSubject();
//
//		System.out.println();
//		System.out.println(subId); 
//		System.out.println(subjectId); 
//
//		String message = null;
//		if (studentScoreMapper.updateScoreById(studentSubject, subjectId, subId, stuId) > 0) {
//			message = "Update Student Subject Success";
//		}
//		model.addAttribute("subjectList", listSubject);
//		model.addAttribute("student", student);
//		model.addAttribute("subject", subject);
//		model.addAttribute("studentSubject", studentScore);
//		model.addAttribute("message", message);
//		
//		return "redirect:/admin/student/subject/detail?stuId=" + stuId + "&subId=" + subjectId;
//	}

//	@GetMapping("/subject/detail")
//	public String getSubjectDetailById(Model model, @RequestParam(name = "subId") int subId,
//			@RequestParam(name = "stuId") int stuId) {
//
//		Student_Score studentScore = studentScoreMapper.getStudentScoreById(stuId, subId);
//
//		Student student = studentMapper.getStudent(stuId, null);
//
//		List<Subject> listSubject = subjectMapper.getListSubject();
//		Subject subject = subjectMapper.getSubjectById(subId);
//
//		System.out.println();
//		System.out.println(stuId);
//		System.out.println(subId);
//
//		model.addAttribute("subjectList", listSubject);
//		model.addAttribute("student", student);
//		model.addAttribute("subject", subject);
//		model.addAttribute("studentSubject", studentScore);
//
//		return "admin/StudentSubjectDetail";
//	}

}
