package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Subject;

@Mapper
public interface SubjectMapper {
  
//	List<Subject> listSubjectById(@Param("tea_id") int id);
	
	List<Subject> listSubject(@Param("stuId") int id);
	
	
}