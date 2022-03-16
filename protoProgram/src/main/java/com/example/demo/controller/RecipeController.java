package com.example.demo.controller;

import java.awt.print.Printable;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Recipe;
import com.example.demo.service.RecipeService;

@RestController
public class RecipeController {
	@Autowired
	RecipeService recipeService;
	
	
	
	@GetMapping("/getContents")
	public String getContens(@RequestParam String id)throws Exception{
		return recipeService.getContents(id);
	}
	
	
	//url로 레시피 db저장
	@SuppressWarnings("null")
	@GetMapping("/insert")
	public String insertRecipe(@RequestParam String url) throws Exception{
		Recipe recipe = new Recipe();
		String content = null;
		String picture = null;
		//String url = "https://www.10000recipe.com/recipe/6864674"; //크롤링할 url지정
		Document doc = null;        //Document에는 페이지의 전체 소스가 저장된다
		System.out.println("1");
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//요리 제목
		Elements element = doc.select("#contents_area > div.view2_summary.st3 > h3"); 		
		
		recipe.setName(element.text());
		
		//요리 재료
		element = doc.select("#divConfirmedMaterialArea > ul > a"); 
		recipe.setIngredient(element.text());
		
		//조리순서
		element = doc.select("div.view_step"); 
		//Iterator을 사용하여 하나씩 값 가져오기
		Iterator<Element> ie1 = element.select("div.media-body").iterator();
		if(ie1.hasNext()) {
			content = ie1.next().text();
		}
		while (ie1.hasNext()) {
			String temp = content +"@"+ ie1.next().text();
			content = temp;
		}
		recipe.setContent(content);
		
		//레시피사진
		element = doc.select("#main_thumbs");
		picture = element.attr("abs:src");
		recipe.setPicture(picture);
		
		return recipeService.insertRecipe(recipe);
	}
	
	//url로 레시피 db저장
		@GetMapping("/insert2")
		public String insertRecipe2(@RequestParam String url) throws Exception{
			Recipe recipe = new Recipe();
			String content = null;
			String picture = null;
			String recipePicture = null;
			String tag = null;
			Document doc = null;        //Document에는 페이지의 전체 소스가 저장된다
			
			try {
				doc = Jsoup.connect(url).get();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			//요리 제목
			Elements element = doc.select("#container > div.inpage-recipe > div > div.view_recipe > section.sec_info > div > div.top > h1 > strong"); 		
			recipe.setName(element.text());
			//요리 재료
			element = doc.select("#container > div.inpage-recipe > div > div.view_recipe > section.sec_info > div > div.btm > ul"); 
			recipe.setIngredient(element.text());
			
			//조리과정
			element = doc.select("#container > div.inpage-recipe > div > div.view_recipe > section.sec_detail > section.sec_rcp_step > ol"); 
			//Iterator를 사용하여 조리과정 텍스트 가져오기
			Iterator<Element> ie1 = element.select("li > p").iterator();			
			if(ie1.hasNext()) {
				content = ie1.next().text();				
			}
			while (ie1.hasNext()) {
				content += "@"+ ie1.next().text();
			}
			recipe.setContent(content);
			
			//Iterator를 사용하여 조리과정 사진들 가져오기
			Iterator<Element> ie2 = element.select("li").iterator();
			if(ie2.hasNext()) {			
				recipePicture = ie2.next().select("img").attr("src");				
			}
			while (ie2.hasNext()) {				
				recipePicture += "@" + ie2.next().select("img").attr("src");
			}			
			recipe.setRecipePicture(recipePicture);
			
			//레시피태그
			element = doc.select("#container > div.inpage-recipe > div > div.view_recipe > section.sec_detail > div.box_tag");
			Iterator<Element> ie3 = element.select("a").iterator();
			if(ie3.hasNext()) {
				tag = ie3.next().text();				
			}
			while (ie3.hasNext()) {
				tag += "@"+ ie3.next().text();
			}
			
			recipe.setTag(tag);
			
			//레시피섬네일
			element = doc.select("#main_thumbs");
			picture = element.attr("abs:src");
			recipe.setPicture(picture);
			
			return recipeService.insertRecipe(recipe);
		}
	
}
