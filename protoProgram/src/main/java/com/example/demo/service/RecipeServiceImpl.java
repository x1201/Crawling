package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.Recipe;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firestore.v1.Document;

@Service
public class RecipeServiceImpl implements RecipeService{
	public static final String COLLECTION_NAME = "recipe";
	
	@Override
	public String insertRecipe(Recipe recipe) throws Exception{
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<DocumentReference> apiFuture = 
				firestore.collection(COLLECTION_NAME).add(recipe);
		return apiFuture.get().getId();
	}
	
	@Override
	   public String getContents(String id)throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        Recipe recipe = null;
        if(documentSnapshot.exists()) {
                recipe = documentSnapshot.toObject(Recipe.class);
               return recipe.getContent();
        }
        else {
                 return null;
        }     
	   
	}

}
