package com.quanlytrungtamdayhoc.controller.TeacherController;

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
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.AccountMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/teacher")
public class TeacherProfileController {
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		
		Teacher teacher = teacherMapper.getTeacher(0, currentAccount.getAccUsername());
		
		model.addAttribute("teacher", teacher);
		return "teacher/TeacherProfile";
	}
	
	@PostMapping("/profile")
	public String updateProfile(Model model, Principal principal,
								@ModelAttribute(value="teacher") Teacher teacher, 
								//@RequestBody Teacher teacher,
								@RequestParam(name = "birthdate") String birthdate,
								@RequestParam(name = "confirmPass") String confirmPass,
								@RequestParam(name = "newPass") String newPass) {
		String message = null;
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		
		
		if(teacherMapper.updateTeacher(teacher, birthdate) > 0) {
			message = "Successfully update information";
			
			if(!newPass.equals("")){
				if(newPass.equals(confirmPass) && accountMapper.updatePassword(currentAccount.getAccUsername(), passwordEncoder.encode(newPass)) > 0) {
					message = "Successfully update password";
				}else {
					message = "Update password failed";
				}
			}
		}else {
			message = "Update information failed";
		}
		
		teacher = teacherMapper.getTeacher(0, currentAccount.getAccUsername());
		
		model.addAttribute("teacher", teacher);
		model.addAttribute("message", message);
		return "teacher/TeacherProfile";
	}
}
