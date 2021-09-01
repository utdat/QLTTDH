package com.quanlytrungtamdayhoc.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quanlytrungtamdayhoc.dbo.Account;
import com.quanlytrungtamdayhoc.mapper.AccountMapper;

@Service
public class AccountService implements UserDetailsService{

	@Autowired
	private AccountMapper account_mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = account_mapper.getAccount(username);
		
		if(account == null) {
			 throw new UsernameNotFoundException("Not found");
		}
		else {
			if(account.getAccRole() == 1) {
				account.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_STUDENT")));
			}
			else if (account.getAccRole() == 2) {
				account.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_TEACHER")));
			}
			else {
				account.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
			}
		}

		return account;
	}
	
	
}
