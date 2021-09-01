package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Student_score;

@Mapper
public interface Student_scoreMapper {
  
	List<Student_score> getListStudentScore(@Param("stu_id") int id);
}