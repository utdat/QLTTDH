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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		String email = currentAccount.getUsername();

		Student student = studentMapper.getStudentByEmail(email);
		model.addAttribute("student", student);
		return "student/student_profile";
	}

	@PostMapping("/profile")
	public String editProfile(Model model, Principal principal, @ModelAttribute(value = "student") Student student,
			@RequestParam(name = "confirmPass") String confirmPass, @RequestParam(name = "newPass") String newPass,
			@RequestParam(name = "birthdate") String birthdate, RedirectAttributes redirectAttributes) {

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();

		if (studentMapper.updateStudent(student, currentAccount.getAccUsername(), birthdate) > 0) {
			if (!newPass.equals("")) {
				if (newPass.equals(confirmPass) && accountMapper.updatePassword(currentAccount.getAccUsername(),
						passwordEncoder.encode(newPass)) > 0) {
					redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
				} else {
					redirectAttributes.addFlashAttribute("error", "Cập nhật thất bại");
				}
			}
			redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
		} else {
			redirectAttributes.addFlashAttribute("error", "Cập nhật thất bại");
		}

		student = studentMapper.getStudentByEmail(currentAccount.getAccUsername());
		model.addAttribute("student", student);
		return "redirect:/student/profile";
	}

}
