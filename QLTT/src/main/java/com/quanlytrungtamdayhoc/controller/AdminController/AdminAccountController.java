package com.quanlytrungtamdayhoc.controller.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	@GetMapping("/account/student")
	public String getAccountStudent(Model model) {
		
		List<Student> studentList = studentMapper.getAllStudent();
		model.addAttribute("studentList", studentList);
		
		return "admin/AccountStudent";
	}
	
	@GetMapping("/account/student/{stuId}")
	public String getStudentDetail(Model model, @PathVariable int stuId) {
		
		Student student = studentMapper.getStudent(stuId, null);
		model.addAttribute("student", student);
		
		return "admin/StudentDetail";
	}
	
	@GetMapping("/account/teacher")
	public String getAccountTeacher(Model model) {
		
		List<Teacher> teacherList = teacherMapper.getAllTeacher();
		model.addAttribute("teacherList", teacherList);
		
		return "admin/AccountTeacher";
	}
	
	@GetMapping("/account/teacher/{teaId}")
	public String getTeacherDetail(Model model, @PathVariable int teaId) {
		
		Teacher teacher = teacherMapper.getTeacher(teaId, null);
		model.addAttribute("teacher", teacher);
		
		return "admin/TeacherDetail";
	}
	
	@GetMapping("/account/detail")
	public String getAccountDetail(Model model) {
		
		Student student = new Student();
		model.addAttribute("student", student);
		
		return "admin/StudentDetail";
	}
}