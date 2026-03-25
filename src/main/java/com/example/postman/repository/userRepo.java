package com.example.postman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postman.model.User;

public interface userRepo extends JpaRepository<User,Long>{
	User findByUsername(String username);
}
