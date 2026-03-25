package com.example.postman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.postman.TokenUtil;
import com.example.postman.model.User;
import com.example.postman.repository.noteRepo;
import com.example.postman.repository.userRepo;

@RestController
@RequestMapping("/auth") 
public class AuthController {
	@Autowired
	private userRepo repo;
	
	@Autowired
	private TokenUtil tokenutil;
	
	@Autowired
	private noteRepo noterepo;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		if(user.getRole()==null) {
			user.setRole("USER");
		}
		repo.save(user);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("User Registered");
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		User dbUser=repo.findByUsername(user.getUsername());
		if(dbUser!=null ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("User not found");
		}
		if(!dbUser.getPassword().equals(user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid password");
		}
		String token=tokenutil.generateToken(dbUser.getUsername(),dbUser.getRole());
        return ResponseEntity.ok(token);	
	}
	
}
