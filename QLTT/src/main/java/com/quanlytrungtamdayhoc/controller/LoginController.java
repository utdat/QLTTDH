package com.quanlytrungtamdayhoc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
public class LoginController {
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Autowired
	private StudentMapper studentMapper;

	@GetMapping("/login")
	public String Login(Model model) {

		return "Login";
	}
	
	@GetMapping("/userInfo")
	public String UserInfo(Model model, Principal principal) {
		String view = "";
		
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		
		if(currentAccount.getAccRole() == 2) {
			Teacher teacher = teacherMapper.getTeacher(0, currentAccount.getAccUsername());
			view = "teacher/TeacherProfile";
			model.addAttribute("teacher", teacher);
		}else if(currentAccount.getAccRole() == 1) {
			view = "Login";
		}else {
			List<Student> studentList = studentMapper.getAllStudent();
			model.addAttribute("studentList", studentList);
			view = "admin/AccountStudent";
		}
		
		model.addAttribute("message", "Successfully logged in");
		
		return view;
	}
}
