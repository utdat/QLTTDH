package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Subject;

@Mapper
public interface SubjectMapper {
	
	List<Subject> getAllSubject();
	
	List<Subject> getTeacherSubject(@Param("teaId") int teaId,
									@Param("year") String year,
									@Param("subName") String subName);
	
	List<Subject> listStudentSubject(@Param("stuId") int stuId,
									 @Param("teaName") String teaName,
									 @Param("subName") String subName);
}