package com.cricket.project.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cricket.project.Exception.UserNotFound;
import com.cricket.project.entity.Admin;
import com.cricket.project.repositories.AdminRepository;
import com.cricket.project.service.AdminService;

@Component
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	AdminRepository repo;

	@Override
	public boolean adminLog(Admin admin) throws UserNotFound {
		
		if(repo.findUser(admin.getName(), admin.getPassword())==null)
		{
			throw new UserNotFound("Incorrect credentials ");
		}
		
		return true;
		
	}
	
	
	

}
