package com.example.name_generator_api.model;

import java.util.ArrayList;

public class NameList extends ArrayList<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameList() {
		super();
	}
	
	private Long id;
	private String name;
	//private Long owner;
	private String description;
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*
	public Long getOwner() {
		return owner;
	}
	*/
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
