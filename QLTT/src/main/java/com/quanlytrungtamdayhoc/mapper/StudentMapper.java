package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Student;

@Mapper
public interface StudentMapper {
	List<Student> getAllStudent();

	Student getStudentById(@Param("studentId") int id);

	Student getStudentByEmail(@Param("studentEmail") String email);

	Student getStudent(@Param("stuId") int stuId, @Param("stuEmail") String stuEmail);

	int updateStudent(@Param("student") Student student, @Param("email") String email,
			@Param("birthdate") String birthdate);
}