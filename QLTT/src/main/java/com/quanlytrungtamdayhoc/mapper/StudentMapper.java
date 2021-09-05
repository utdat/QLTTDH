package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Student;
@Mapper
public interface StudentMapper {
	
	List<Student> getAllStudent();
	
	List<Student> getStudentByFilter(@Param("stuName") String stuName,
					  				@Param("stuPhone") String stuPhone);		
	
	Student getStudent(@Param("stuId") int stuId, 
					   @Param("stuEmail") String stuEmail);

	int updateStudent(@Param("student") Student student,
					  @Param("birthdate") String birthdate);
	
	int addStudent(@Param("stuName") String stuName,
				   @Param("stuPhone") String stuPhone,
				   @Param("stuSchool") String stuSchool,
				   @Param("stuEmail") String stuEmail);
}