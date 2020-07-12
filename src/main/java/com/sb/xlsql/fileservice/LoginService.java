package com.sb.xlsql.fileservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.xlsql.model.User;
import com.sb.xlsql.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	UserRepository repository;
	
	
	public User isValidUser(String loginid, String password) {
		return repository.isValidUser(loginid, password);
	}
}
