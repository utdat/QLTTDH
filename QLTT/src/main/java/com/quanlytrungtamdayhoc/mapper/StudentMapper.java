package com.quanlytrungtamdayhoc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Student;
import com.quanlytrungtamdayhoc.dbo.Teacher;

@Mapper
public interface StudentMapper {
 
	Student getStudentById(@Param("student_id") int id);
	
	Student getStudentByEmail(@Param("student_email") String email);
	
	int updateStudent(@Param("student") Student student, @Param("email") String email,
			  @Param("birthdate") String birthdate);
}