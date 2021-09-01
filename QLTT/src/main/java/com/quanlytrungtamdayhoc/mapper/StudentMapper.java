package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Student;

@Mapper
public interface StudentMapper {
	
	List<Student> getAllStudent();
	
	Student getStudent(@Param("stuId") int stuId, @Param("stuEmail") String stuEmail);
}