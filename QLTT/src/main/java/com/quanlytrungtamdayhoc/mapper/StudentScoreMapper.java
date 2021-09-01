package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.StudentScore;

@Mapper
public interface StudentScoreMapper {

//	List<Student_Score> getTeacherMark(@Param("teaId") int teaId, @Param("subId") int subId);

	List<StudentScore> getListStudentScore(@Param("stu_id") int id);

	int updateScore(@Param("subId") int subId, @Param("stuId") int stuId, @Param("score") float score);

	int insertSubjectScore(@Param("stuId") int stuId, @Param("subId") int subId, @Param("score") float score);
}
