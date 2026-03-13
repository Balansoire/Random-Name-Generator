package com.example.name_generator_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.name_generator_api.dto.GenerateNameRequest;
import com.example.name_generator_api.dto.GenerateNameResponse;
import com.example.name_generator_api.dto.GetMethodsResponse;
import com.example.name_generator_api.exception.BadArgsException;
import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.exception.ListOfEmptyException;
import com.example.name_generator_api.exception.NotImplementedException;
import com.example.name_generator_api.service.NameGeneratorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/names")
public class NameGeneratorController {

	private final NameGeneratorService service;
	
	public NameGeneratorController(NameGeneratorService service) {
		this.service = service;
	}
	
	@PostMapping("generate")
	public ResponseEntity<GenerateNameResponse> generate(
			@RequestBody GenerateNameRequest request
			) throws EmptyListException, NotImplementedException, BadArgsException, ListOfEmptyException {
		
		String name = service.generateName(
				request.strategy(),
				request.args(),
				request.list()
		);
		
		return ResponseEntity.ok(new GenerateNameResponse(name));
	}
	
	@GetMapping("methods")
	public ResponseEntity<GetMethodsResponse> getMethods() {
		return ResponseEntity.ok(new GetMethodsResponse(service.getMethods()));
	}
}
