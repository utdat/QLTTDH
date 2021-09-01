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
	private TeacherMapper teacherMapper;

	@Autowired
	private StudentMapper studentMapper;

	@GetMapping("/login")
	public ModelAndView Login() {

		return new ModelAndView("login");
	}

	@GetMapping("/userInfo")
	public ModelAndView UserInfo(Principal principal) {
		ModelAndView view = new ModelAndView();

		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();

		if (currentAccount.getAccRole() == 2) {
			Teacher teacher = teacherMapper.getTeacher(1);
			view.setViewName("teacher/teacher_profile");
			view.addObject("teacher", teacher);

		} else if (currentAccount.getAccRole() == 1) {
			Student student = studentMapper.getStudentByEmail(currentAccount.getAccUsername());
			view.setViewName("student/student_profile");
			view.addObject("student", student);
		}

		view.addObject("account", currentAccount);
		return view;
	}
}