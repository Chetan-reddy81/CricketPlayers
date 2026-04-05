package com.cricket.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cricket.project.Exception.UserNotFound;
import com.cricket.project.entity.Admin;
import com.cricket.project.service.AdminService;
//
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
//
	@Autowired
	AdminService as;
	@PostMapping("/adminLogin")
	ResponseEntity<?> admin(@RequestBody Admin admin)
	{
		try
	     {
	    	 as.adminLog(admin);
	     }
	     catch (UserNotFound e) {
			// TODO: handle exception
			 return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); 
		}
	     catch (Exception e) {
			 //TODO: handle exception
	    	 return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE); 
		}
	     
		 return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
