package com.example.name_generator_api.dto;

import java.util.List;

public record GetMethodsResponse(
		List<List<String>> methodList
) {}
