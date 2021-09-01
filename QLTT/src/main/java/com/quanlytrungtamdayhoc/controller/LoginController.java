package com.quanlytrungtamdayhoc.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
public class LoginController {
	@Autowired
	private TeacherMapper teacher_mapper;

	@Autowired
	private StudentMapper student_mapper;

	@GetMapping("/login")
	public ModelAndView Login() {

		return new ModelAndView("login");
	}

	@GetMapping("/userInfo")
	public ModelAndView UserInfo(Principal principal) {
		ModelAndView view = new ModelAndView();

		Account current_account = (Account) ((Authentication) principal).getPrincipal();

		if (current_account.getAccRole() == 2) {
			Teacher teacher = teacher_mapper.getTeacher(1);
			view.setViewName("teacher/teacher_profile");
			view.addObject("teacher", teacher);

		} else if (current_account.getAccRole() == 1) {
			String stu_email = current_account.getAccUsername();
			
			Student student = student_mapper.getStudentByEmail(stu_email);
					
			view.setViewName("student/student_profile");
			view.addObject("student", student);
		}

		view.addObject("account", current_account);
		return view;
	}
}