package com.quanlytrungtamdayhoc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Teacher;

@Mapper
public interface TeacherMapper {
   
	Teacher getTeacher(@Param("tea_email") String tea_email);
	int updateTeacher(@Param("teacher") Teacher teacher, 
					  @Param("email") String email,
					  @Param("birthdate") String birthdate);

	;
}