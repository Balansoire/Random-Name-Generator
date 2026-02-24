package com.example.name_generator_api.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.name_generator_api.exception.BadArgsException;
import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.exception.NotImplementedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BadArgsException.class)
	public ResponseEntity<String> handleBadArgs(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(EmptyListException.class)
	public ResponseEntity<String> handleEmptyList() {
		return ResponseEntity.badRequest().body("Name list is empty");
	}
	
	
	@ExceptionHandler(NotImplementedException.class)
	public ResponseEntity<String> handleNotImplemented(Exception e) {
		return ResponseEntity.badRequest().body("Strategy" + e.getMessage());
	}
}
