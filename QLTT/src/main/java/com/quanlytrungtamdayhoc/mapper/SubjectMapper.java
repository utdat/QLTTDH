package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Subject;

@Mapper
public interface SubjectMapper {
	
	List<Subject> getAllSubject(@Param("teaName") String teaName,
			 					@Param("year") String year);
	
	List<Subject> getTeacherSubject(@Param("teaId") int teaId,
									@Param("year") String year,
									@Param("subName") String subName);
	
	List<Subject> listStudentSubject(@Param("stuId") int stuId,
									 @Param("teaName") String teaName,
									 @Param("subName") String subName);
	
	Subject getSubject(@Param("subId") int subId);
	
	int insertSubject(@Param("subName") String subName, 
					  @Param("subStartdate") String subStartdate,
					  @Param("subSchedule") String subSchedule, 
					  @Param("subRoom") String subRoom,
					  @Param("subTuition") int tuition, 
					  @Param("teaId") int teaId);
	
	int deleteSubject(@Param("subId") int subId);
	
	int updateSubject(@Param("subject") Subject subject,
					  @Param("teaId") int teaId,
					  @Param("subStartdate") String subStartdate);
}