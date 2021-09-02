package com.quanlytrungtamdayhoc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Teacher;
import com.quanlytrungtamdayhoc.mapper.StudentMapper;
import com.quanlytrungtamdayhoc.mapper.TeacherMapper;

@Controller
public class UploadImageController {

	@Autowired
	private TeacherMapper teacherMapper;
	
	@Autowired
	private StudentMapper studentMapper;

	
	@PostMapping("/upload")
	public String uploadImage(@RequestParam(name = "avatar") MultipartFile multipartFile,
							  RedirectAttributes redir,
							  Principal principal) throws IOException{
		
		Account currentAccount = (Account) ((Authentication) principal).getPrincipal();
		String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String uploadDir = "./src/main/resources/static/images/";
		String redirect = "";
		
		if(currentAccount.getAccRole() == 1) {
			Student student = studentMapper.getStudent(0, currentAccount.getAccUsername());
			student.setStuAvatar(filename);
			studentMapper.updateStudent(student, null);
			uploadDir += student.getStuId();
			redirect = "redirect:/student/profile";
			
		}else if(currentAccount.getAccRole() == 2) {
			Teacher teacher = teacherMapper.getTeacher(0, currentAccount.getAccUsername());
			teacher.setTeaAvatar(filename);
			teacherMapper.updateTeacher(teacher, null);
			uploadDir += teacher.getTeaId();
			redirect = "redirect:/teacher/profile";
			
		}

		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(filename);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Could not save upload file: " + filename);
		}
		return redirect;
	}
	
}
