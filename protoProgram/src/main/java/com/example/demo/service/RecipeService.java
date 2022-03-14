package com.example.demo.service;

import com.example.demo.Recipe;

public interface RecipeService {
	public String insertRecipe(Recipe recipe) throws Exception;
	public String getContents(String id)throws Exception;
}
