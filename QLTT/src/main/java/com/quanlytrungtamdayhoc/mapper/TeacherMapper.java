package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Teacher;

@Mapper
public interface TeacherMapper {

	Teacher getTeacher(@Param("teaId") int teaId, @Param("teaEmail") String teaEmail);

	Teacher getTeacherByEmail(@Param("teaEmail") String email);

	Teacher getTeacherById(@Param("teaId") int id);

	List<Teacher> getAllTeacher();
	
	int updateTeacher(@Param("teacher") Teacher teacher, 
			  @Param("email") String email,
			  @Param("birthdate") String birthdate);
}