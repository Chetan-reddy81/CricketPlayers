package com.cricket.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cricket.project.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	
	@Query("select a from admin a where a.name=?1 and a.password=?2")
	Admin findUser(String name,String password);
}
