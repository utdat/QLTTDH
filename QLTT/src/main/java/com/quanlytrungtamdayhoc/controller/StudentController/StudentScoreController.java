package com.quanlytrungtamdayhoc.controller.StudentController;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Student_Score;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.StudentScoreMapper;

@Controller
@RequestMapping("/student")
public class StudentScoreController {

	@Autowired
	StudentMapper studentmapper;

	@Autowired
	StudentScoreMapper studentScoreMapper;

	@GetMapping("/score")
	public String getStudentScore(Model model, Principal principal) {

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentmapper.getStudent(0, currentAccount.getAccUsername());
		
		List<Student_Score> list = studentScoreMapper.getStudentScore(student.getStuId());
		
		model.addAttribute("listStudentScore", list);
		model.addAttribute("student", student);
		
		return "student/StudentScore";
	}

	@GetMapping("/score/delete")
	public String deleteStudentSubject(Model model, Principal principal,
									   @RequestParam(name = "subId") int subId) {
		
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		Student student = studentmapper.getStudent(0, currentAccount.getAccUsername());
		
		if (studentScoreMapper.deleteStudentSubject(subId, student.getStuId()) > 0) {
			return "redirect:/student/score";
		}
		
		model.addAttribute("student", student);
		return "student/StudentScore";
	}
}
