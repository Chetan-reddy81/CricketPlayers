package com.cricket.project.service;

import org.springframework.stereotype.Service;

import com.cricket.project.Exception.UserNotFound;
import com.cricket.project.entity.Admin;

@Service
public interface AdminService {

	boolean adminLog(Admin admin) throws UserNotFound;
}
