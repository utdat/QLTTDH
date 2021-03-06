package com.quanlytrungtamdayhoc.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.quanlytrungtamdayhoc.service.AccountService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AccountService accountService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
	    .antMatchers("/css/**", "/images/**").permitAll()
	    .antMatchers("/admin/**").hasRole("ADMIN")
	    .antMatchers("/teacher/**").hasRole("TEACHER")
	    .antMatchers("/student/**").hasRole("STUDENT")
	    .anyRequest().authenticated().and()
		.formLogin().loginPage("/login").permitAll()
		.defaultSuccessUrl("/userInfo")
		.failureUrl("/login?success=false")
		.loginProcessingUrl("/spring_security_check").and()
		.logout().logoutSuccessUrl("/login").and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.inMemoryAuthentication()
		 * .withUser("abc").password(passwordEncoder().encode("123"))
		 * .authorities("ROLE_USER");
		 */
		
		auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
	}
}
