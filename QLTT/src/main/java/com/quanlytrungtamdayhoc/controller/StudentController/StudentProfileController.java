package com.quanlytrungtamdayhoc.controller.StudentController;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.mapper.AccountMapper;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;

@Controller
@RequestMapping("/student")
public class StudentProfileController {

	@Autowired
	StudentMapper studentMapper;

	@Autowired
	AccountMapper accountMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();

		Student student = studentMapper.getStudent(0, currentAccount.getUsername());
		
		model.addAttribute("student", student);
		return "student/StudentProfile";
	}

	@PostMapping("/profile")
	public String updateProfile(Model model, Principal principal, 
								@ModelAttribute(value = "student") Student student,
								@RequestParam(name = "confirmPass") String confirmPass, 
								@RequestParam(name = "newPass") String newPass,
								@RequestParam(name = "birthdate") String birthdate) {

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		String message = null;

		if (studentMapper.updateStudent(student, birthdate) > 0) {
			message = "Successfully update information";
			
			if (!newPass.equals("")) {
				if (newPass.equals(confirmPass) && accountMapper.updatePassword(currentAccount.getAccUsername(), passwordEncoder.encode(newPass)) > 0) {
					message = "Successfully update password";
				} else {
					message = "Update password failed";
				}
			}
		} else {
			message = "Update information failed";
		}

		student = studentMapper.getStudent(0, currentAccount.getAccUsername());
		
		model.addAttribute("student", student);
		model.addAttribute("message", message);
		return "/student/StudentProfile";
	}
}
