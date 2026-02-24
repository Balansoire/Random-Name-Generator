package com.example.name_generator_api.dto;

import com.example.name_generator_api.model.NameList;

public record GenerateNameRequest(
		String strategy,
		String[][] args,
		NameList list
) {}
