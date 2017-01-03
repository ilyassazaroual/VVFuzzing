package com.FuzzerVV.client;

import static org.junit.Assert.assertEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class DELETEREQUEST {

	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String BASE_URL = "http://localhost:8080";
	public static final String PATH_ENTRY = "/pets";


	// public static RepeatedTest suite() {
	// return new RepeatedTest(new TestSuite(JerseyClientTest.class), 10);
	// }

	@Test
	public void DeleteRequestsOK(Map.Entry<String, Path> entry) {
		try {		
			System.out.println("===========================================================================================\n");

			System.out.println("[INFO] - DEBUT TRAITEMENT TEST DELETE_REQUEST_OK ");
			Client client = Client.create();
			
			Swagger swagger = new SwaggerParser().read("swagger.json");
			int idToDelete = 0;
			String FullUrl=BASE_URL+swagger.getBasePath()+PATH_ENTRY+"/"+idToDelete;
			WebResource webResource = client.resource(FullUrl);

			
			 ClientResponse response = webResource.accept("application/json")
		                .type("application/json").delete(ClientResponse.class);
			
				Operation getDelete = swagger.getPaths().get("/pets/{id}").getDelete();
				
				int status = response.getStatus();
				System.out.println("\t[INFO] - Debut testing STATUS CODE");
				if (status != 200) {
					Response test = getDelete.getResponses().get(status);
					if (test != null){
						throw new Exception("\t[KO] - erreur serveur: erreur " + test.getDescription());
					}
					else{
						throw new Exception("\t[KO] - erreur serveur: SWAGGER DOESN'T SUPPORT THIS STATUS");
					}
				}
				assertEquals(status, 200);
				System.out.println("\t[OK] - fin testing STATUS CODE OK : " + status);				
				System.out.println("===========================================================================================\n\n");

		}
		catch (Exception e) {

			System.out.println("[KO] - FIN TRAITEMENT TEST DELETE_REQUEST_KO \n\n");
			e.printStackTrace();

		}

	}

}