package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Student_Score;

@Mapper
public interface StudentScoreMapper {

	List<Student_Score> getTeacherMark(@Param("teaId") int teaId, @Param("subId") int subId);

	List<Student_Score> getStudentScore(@Param("stuId") int stuId);

	List<Student_Score> getListStudentScore();

	Student_Score getStudentScoreById(@Param("stuId") int stuId, @Param("subId") int subId);

	int updateScore(@Param("subId") int subId, @Param("stuId") int stuId, @Param("score") float score);

	int updateScoreById(@Param("studentSubject") Student_Score studentSubject,@Param("subId") int subId, @Param("stuId") int stuId);

	int insertSubjectScore(@Param("stuId") int stuId, @Param("subId") int subId);

	int deleteStudentSubject(@Param("subId") int subId, @Param("stuId") int stuId);
}
