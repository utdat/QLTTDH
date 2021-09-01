package com.quanlytrungtamdayhoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.quanlytrungtamdayhoc.dbo.Account;

@Mapper
public interface AccountMapper {
	
	List<Account> getAllAccount();
	
	Account getAccount(@Param("username") String username);
}