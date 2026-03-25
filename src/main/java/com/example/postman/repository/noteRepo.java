package com.example.postman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postman.model.Note;

public interface noteRepo extends JpaRepository<Note,Long> {
	List<Note> findByUsername(String username);
}
