package com.example.demo.controller;

import java.security.Principal;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@GetMapping()
	public String home(Principal principal,HttpSession session) {
		if(principal!=null) {
			String username = principal.getName();
			session.setAttribute("username1", username);
		}
		return "home";
	}
	@GetMapping("/admin")
	public String admin() {
		return "index";
	}
	@GetMapping("/shipper")
		public String shipper() {
			return "responsive_table";
		}
}
