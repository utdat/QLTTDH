package com.quanlytrungtamdayhoc.controller.StudentController;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Student_score;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.Student_scoreMapper;

@Controller
@RequestMapping("/student")
public class StudentScoreController {

	@Autowired
	StudentMapper studentmapper;

	@Autowired
	Student_scoreMapper studentscoremapper;

	@GetMapping("/score")
	public String getStudentScore(Model model, Principal principal) {
		
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		String email = current_account.getAccUsername();
		
		Student s = studentmapper.getStudentByEmail("rhoncus.Nullam.velit@velitegestaslacinia.net");
		int stu_id = s.getStuId();
		
		List<Student_score> list = studentscoremapper.getListStudentScore(stu_id);
		model.addAttribute("list_student_score", list);
		
		return "student/student_score";
	}
}
