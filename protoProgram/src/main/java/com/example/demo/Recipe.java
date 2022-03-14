package com.example.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Recipe {

	private String name;
	private String ingredient;
	private String content;
	private String Picture;
	private String recipePicture;
	
}
