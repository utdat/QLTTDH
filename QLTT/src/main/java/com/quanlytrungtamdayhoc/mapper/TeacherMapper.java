package com.quanlytrungtamdayhoc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Teacher;

@Mapper
public interface TeacherMapper {
 
	Teacher getTeacherByEmail(@Param("tea_email") String email);
	
	Teacher getTeacher(@Param("tea_id") int id);
}