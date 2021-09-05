package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Account;

@Mapper
public interface AccountMapper {
	
	List<Account> getAllAccount();
	
	List<Account> getAccountByRole(@Param("role") int role,
								   @Param("name") String name,
								   @Param("phone") String phone);
	
	Account getAccount(@Param("username") String username);
	
	int updatePassword(@Param("username") String username,
					   @Param("password") String password);
	
	int activateAccount(@Param("username") String username,
			   			@Param("isActive") String isActive);
	
	int addAccount(@Param("username") String username,
				   @Param("password") String password,
				   @Param("role") int role);
	
	int deleteAccount(@Param("username") String username);
}