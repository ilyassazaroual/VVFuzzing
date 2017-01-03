package com.FuzzerVV.client;

import java.io.IOException;
import java.util.Map;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class Main {
	

	
	public static void main(String[] args) {
		
		System.out.println("[INFO] - DEBUT TRAITEMENT SIMPLE DEMO ");

		simpleDemo(0);
		
		System.out.println("[INFO] - FIN TRAITEMENT SIMPLE DEMO ");
		
		
		for (int i=1;i<=100;i++){
			System.out.println("[INFO] - DEBUT TRAITEMENT SURCHAGE SWAGGER DEMO N: "+i);
			simpleDemo(0);
			System.out.println("[INFO] - FIN TRAITEMENT SURCHAGE SWAGGER DEMO N: "+i);
		}
		
		
		
		
		System.out.println("[INFO] - DEBUT TRAITEMENT FUZZING ");
		
		System.out.println("[INFO] - DEBUT TEST ENVOIE ENTREE VIDE ");
		
		simpleDemo(1);
		
		System.out.println("[INFO] - FIN TEST ENVOIE ENTREE VIDE ");
		
		System.out.println("[INFO] - DEBUT TEST ENTREE FUZZING ");
		
    	simpleDemo(2);
		
		System.out.println("[INFO] - FIN TEST ENTREE FUZZING ");
		
		System.out.println("[INFO] - FIN TRAITEMENT FUZZING ");


		
	}
	
	public static void simpleDemo(int fuzz){
	
		 GETESTREQUEST callGet =new GETESTREQUEST();
		 POSTREQUEST callPost =new POSTREQUEST();
		 DELETEREQUEST callDelete = new DELETEREQUEST();
		 Swagger swagger = new SwaggerParser().read("swagger.json");
		
		for(Map.Entry<String, Path> entry : swagger.getPaths().entrySet()) {
			if(swagger.getPaths().get(entry.getKey()).getGet()!= null){
				try {
					callGet.GetRequestsOK(entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			if(swagger.getPaths().get(entry.getKey()).getDelete()!= null){
				callDelete.DeleteRequestsOK(entry);
				
			}
			
			if(swagger.getPaths().get(entry.getKey()).getPost()!= null){
				
				callPost.PostRequestsOK(entry,fuzz);
				
			}
			
			
		}
	}
	
	
}
