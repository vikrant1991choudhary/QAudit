package com.sb.xlsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class ExceltoMySqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExceltoMySqlApplication.class, args);
	}
	
	@GetMapping("/")
	public String goToLogin() {
		return "redirect:/login";
	}
	

}
