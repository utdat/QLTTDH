package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Subject;

@Mapper
public interface SubjectMapper {
	
	List<Subject> getTeacherSubject(@Param("teaId") int id);
	
	List<Subject> listStudentSubject(@Param("stuId") int id);
}