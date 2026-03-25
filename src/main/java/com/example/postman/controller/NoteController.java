package com.example.postman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.postman.TokenUtil;
import com.example.postman.model.Note;
import com.example.postman.repository.noteRepo;

@RestController
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	private TokenUtil tokenutil;
	
	@Autowired
	private noteRepo repo;
	
	@PostMapping
	public ResponseEntity<?> createNote(@RequestBody Note note,Authentication auth) {
		String username=auth.getName();
		note.setUsername(username);
		return ResponseEntity.status(HttpStatus.CREATED).body(repo);
	}
	
	@GetMapping
	public ResponseEntity<?> getNotes(Authentication auth) {
		String username=auth.getName();
		return ResponseEntity.ok(repo.findByUsername(username));
	}
	
}
