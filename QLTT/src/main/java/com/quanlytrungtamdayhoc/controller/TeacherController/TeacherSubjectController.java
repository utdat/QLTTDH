package com.quanlytrungtamdayhoc.controller.TeacherController;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student_Score;
import com.quanlytrungtamdayhoc.dbo.Subject;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentScoreMapper;
import com.quanlytrungtamdayhoc.mapper.SubjectMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
@RequestMapping("/teacher")
public class TeacherSubjectController {
	
	@Autowired
	private StudentScoreMapper studentScoreMapper;
	
	@Autowired
	private SubjectMapper subjectMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	@GetMapping("/schedule")
	public String getSubject(Model model, Principal principal) {
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		Teacher teacher = teacherMapper.getTeacher(current_account.getAccUsername());
		
		List<Subject> teacherSubject = subjectMapper.getTeacherSubject(teacher.getTeaId());
		
		model.addAttribute("teacherSubject", teacherSubject);

		return "teacher/TeacherSchedule";
	}
	
	@GetMapping("/mark/{subId}")
	public String markScore(Model model, Principal principal, @PathVariable int subId) {
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		Teacher teacher = teacherMapper.getTeacher(current_account.getAccUsername());
		
		List<Student_Score> studentScore = studentScoreMapper.getTeacherMark(teacher.getTeaId(), subId);
		
		model.addAttribute("studentScore", studentScore);

		return "teacher/TeacherMark";
	}
	
	@GetMapping("/mark")
	public String markScoreAll(Model model, Principal principal) {
		Account current_account = (Account) ((Authentication) principal).getPrincipal();
		Teacher teacher = teacherMapper.getTeacher(current_account.getAccUsername());
		
		List<Student_Score> studentScore = studentScoreMapper.getTeacherMark(teacher.getTeaId(), 0);
		
		model.addAttribute("studentScore", studentScore);

		return "teacher/TeacherMark";
	}
	
	@GetMapping("/mark/update")
	public String updateScore(Model model, Principal principal,
							  @RequestParam(name = "subId") int subId,
							  @RequestParam(name = "stuId") int stuId,
							  @RequestParam(name = "score") float score) {
		
		if(score < 0 || score > 10) {
			return "redirect:/teacher/mark/" + subId;
		}
		
		int kq = studentScoreMapper.updateScore(subId, stuId, score);

		return "redirect:/teacher/mark/" + subId;
	}
}
