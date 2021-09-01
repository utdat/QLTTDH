package com.quanlytrungtamdayhoc.controller.StudentController;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;

@Controller
@RequestMapping("/student")
public class StudentProfileController {

	@Autowired
	StudentMapper student_mapper;

	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		String email = current_account.getUsername();

		Student s = student_mapper.getStudentByEmail(email);
		model.addAttribute("student", s);
		return "student/student_profile";
	}

	@PostMapping("/profile")
	public String editProfile() {
		return "student/student_profile";
	}

}
