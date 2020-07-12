package com.sb.xlsql.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sb.xlsql.fileservice.LoginService;
import com.sb.xlsql.model.User;

@Controller
public class LoginController {

	@Autowired
	LoginService service;
	
	@GetMapping("/login")
	public String Login() {
		return "multipartfile/login.html";
	}
	
	@PostMapping("/login")
	public String dashboard(@RequestParam("loginid") String loginid, @RequestParam("password") String password,Model model,HttpSession session) {
		//System.out.println(loginid+""+password);
		User user = service.isValidUser(loginid, password);
		if(user!=null) {
			session.setAttribute("id", user.getId());
			session.setAttribute("name", user.getName());
			session.setAttribute("email", user.getEmail());
			session.setAttribute("mobile", user.getMobile());
			session.setAttribute("clientid", 1l);
			return "redirect:/auditStatus";
		}
		model.addAttribute("message","Invalid Credentials");
		return "multipartfile/login.html";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session,Model model) {
		session.removeAttribute("id");
		session.removeAttribute("name");
		session.removeAttribute("email");
		session.removeAttribute("mobile");
		session.removeAttribute("companyid");
		session.removeAttribute("audityear");
		
		model.addAttribute("message","Logout Successfull");
		return "multipartfile/login.html";
	}
}
