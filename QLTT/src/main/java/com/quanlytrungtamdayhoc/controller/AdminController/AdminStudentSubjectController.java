package com.quanlytrungtamdayhoc.controller.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/subject")
	public String getSubject(Model model) {
		List<Student_Score> listStudentScore = studentScoreMapper.getListStudentScore();
		List<Student> listStudent = studentMapper.getAllStudent();
		List<Subject> listSubject = subjectMapper.getListSubject();

		model.addAttribute("listStudentSubject", listStudentScore);
		model.addAttribute("listStudent", listStudent);
		model.addAttribute("listSubject", listSubject);
		return "admin/StudentSubject";
	}

	@GetMapping("/subject/detail")
	public String getSubjectDetail(Model model) {
		return "admin/StudentSubjectDetail";
	}

}
