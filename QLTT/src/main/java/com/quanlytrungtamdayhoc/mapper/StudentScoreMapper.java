package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Student_Score;

@Mapper
public interface StudentScoreMapper {

	List<Student_Score> getTeacherMark(@Param("teaId") int teaId, @Param("subId") int subId);
	
	int updateScore(@Param("subId") int subId, @Param("stuId") int stuId, @Param("score") float score);
}
