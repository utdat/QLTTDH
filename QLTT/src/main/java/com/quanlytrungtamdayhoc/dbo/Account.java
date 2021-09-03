package com.quanlytrungtamdayhoc.dbo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Account implements UserDetails{
	private String accUsername;
	private String accPassword;
	private String accIsactive;
	private int accRole;
	private List<GrantedAuthority> authorities = Collections.emptyList();
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.accUsername;
    }
    
    @Override
	public String getPassword() {
		return this.accPassword;
	}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if("T".equals(accIsactive)) {
        	return true;
        }else {
        	return false;
        }
    }
    
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
	
	public String getAccUsername() {
		return accUsername;
	}
	public void setAccUsername(String accUsername) {
		this.accUsername = accUsername;
	}
	public String getAccPassword() {
		return accPassword;
	}
	public void setAccPassword(String accPassword) {
		this.accPassword = accPassword;
	}
	public String getAccIsactive() {
		return accIsactive;
	}
	public void setAccIsactive(String accIsactive) {
		this.accIsactive = accIsactive;
	}
	public int getAccRole() {
		return accRole;
	}
	public void setAccRole(int accRole) {
		this.accRole = accRole;
	}

}
