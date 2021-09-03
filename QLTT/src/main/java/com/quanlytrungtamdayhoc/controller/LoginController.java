package com.quanlytrungtamdayhoc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.AccountMapper;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
public class LoginController {
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private AccountMapper accountMapper;

	@GetMapping("/login")
	public String Login(Model model) {

		return "Login";
	}
	
	@GetMapping("/access-denied")
	public String AccessDenied(Principal principal, RedirectAttributes redirectAttributes) {
		String view = "redirect:/";
		String message = "You do not have permission to access to this page!";
		
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		
		if(currentAccount.getAccRole() == 2) {
			Teacher teacher = teacherMapper.getTeacher(0, currentAccount.getAccUsername());
			view += "teacher/profile";
			
			redirectAttributes.addFlashAttribute("teacher", teacher);
		}else if(currentAccount.getAccRole() == 1) {
			Student student = studentMapper.getStudent(0, currentAccount.getAccUsername());
			view += "student/profile";
			
			redirectAttributes.addFlashAttribute("student", student);
		}else {
			List<Student> studentList = studentMapper.getAllStudent();
			List<Account> accountList = accountMapper.getAccountByRole(1);
			
			redirectAttributes.addFlashAttribute("studentList", studentList);
			redirectAttributes.addFlashAttribute("accountList", accountList);
			view = "admin/account/student";
		}
		
		redirectAttributes.addFlashAttribute("message", message);
		return view;
	}
	
	@GetMapping("/userInfo")
	public String UserInfo(RedirectAttributes redirectAttributes, Principal principal) {
		String view = "redirect:/";
		
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		
		if(currentAccount.getAccRole() == 2) {
			Teacher teacher = teacherMapper.getTeacher(0, currentAccount.getAccUsername());
			view += "teacher/profile";
			
			redirectAttributes.addFlashAttribute("teacher", teacher);
		}else if(currentAccount.getAccRole() == 1) {
			Student student = studentMapper.getStudent(0, currentAccount.getAccUsername());
			view += "student/profile";
			
			redirectAttributes.addFlashAttribute("student", student);
		}else {
			List<Student> studentList = studentMapper.getAllStudent();
			List<Account> accountList = accountMapper.getAccountByRole(1);
			
			redirectAttributes.addFlashAttribute("studentList", studentList);
			redirectAttributes.addFlashAttribute("accountList", accountList);
			view += "admin/account/student";
		}
		
		redirectAttributes.addFlashAttribute("message", "Successfully logged in");
		
		return view;
	}
}
