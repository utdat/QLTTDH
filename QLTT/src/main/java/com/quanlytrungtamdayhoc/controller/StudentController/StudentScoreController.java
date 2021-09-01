package com.quanlytrungtamdayhoc.controller.StudentController;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.StudentScore;
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
		String email = currentAccount.getAccUsername();

		Student s = studentmapper.getStudentByEmail(email);
		int stuId = s.getStuId();

		List<StudentScore> list = studentScoreMapper.getListStudentScore(stuId);
		model.addAttribute("listStudentScore", list);

		return "student/student_score";
	}
}
