package com.sales.sys.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
public class TestRestAPIs {
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return ">>> User Inventory Contents!";
	}

	@GetMapping("/pm")
	@PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
	public String projectManagementAccess() {
		return ">>> Inventory Board Management Project";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return ">>> Admin Inventory Contents";
	}
}