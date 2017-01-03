package com.FuzzerVV.client;

import static org.junit.Assert.assertEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class POSTREQUEST {

	//List<Pet> listPet = new ArrayList<Pet>();
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String BASE_URL = "http://localhost:8080";
	public static final String PATH_ENTRY = "/pets";


	// public static RepeatedTest suite() {
	// return new RepeatedTest(new TestSuite(JerseyClientTest.class), 10);
	// }

	@Test
	public void PostRequestsOK(Map.Entry<String, Path> entry, int fuzz) {
		List<Pet> lis = new ArrayList<Pet>();
		try {
			System.out.println("===========================================================================================\n");

			System.out.println("[INFO] - DEBUT TRAITEMENT TEST POST_REQUEST_OK ");
			Client client = Client.create();
			
			Swagger swagger = new SwaggerParser().read("swagger.json");
			String FullUrl=BASE_URL+swagger.getBasePath()+PATH_ENTRY;
			WebResource webResource = client.resource(FullUrl);
			
				Pet petToPost = new Pet(0, "svennnentry","sun");
				String jsonToSend = null;

				if (fuzz ==1 ){
					petToPost = null;
				}
				

				else if (fuzz == 2) {
					int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
					
					for (int i =0;i<randomNum;i++){
					String uuid = UUID.randomUUID().toString();
					petToPost.setName(uuid);
					uuid = UUID.randomUUID().toString();
					petToPost.setTag(uuid);
					int randomNumber = ThreadLocalRandom.current().nextInt(0, 100000);
					petToPost.setId(randomNumber);
					
					lis.add(petToPost);
					}
					jsonToSend = new Gson().toJson(lis);
				}
				
			if (petToPost!=null && fuzz !=2)	
			jsonToSend = new Gson().toJson(petToPost);
			
			
			
			
			 ClientResponse response = webResource.accept("application/json")
		                .type("application/json").post(ClientResponse.class, jsonToSend);
			 String output = response.getEntity(String.class);
			 

				Operation getPost = swagger.getPaths().get("/pets").getPost();
				int status = response.getStatus();
				System.out.println("\t[INFO] - Debut testing STATUS CODE");
				if (status != 200) {
					try {
					Response test = getPost.getResponses().get(status);
					throw new Exception("\t[KO] - erreur serveur: erreur " + test.getDescription());

					}
					finally{
						System.err.println("\t[KO] - erreur serveur: SWAGGER DOESN'T SUPPORT THIS STATUS: "+status);

					}


				}
				assertEquals(status, 200);
				System.out.println("\t[OK] - fin testing STATUS CODE OK : " + status);
				

			 JSONObject jsonObj = new JSONObject(output);
			 String postValue = jsonObj.getJSONObject("pet").getJSONObject("originalValue").toString();
			 String ReceiveValue = jsonObj.getJSONObject("pet").getJSONObject("value").toString();
			 
			 System.out.println("\t[INFO] - Debut testing VALID JSON DATA");
				boolean testFormatJson = isJSONValid(ReceiveValue);
				if (testFormatJson) {
					assertEquals(testFormatJson, true);
					System.out.println("\t[OK] - Fin testing VALID JSON DATA");
				}
				else {
					System.err.println("\t[KO] - Format données Json non valid");
					assertEquals(testFormatJson, true);
				}
			 
			 
			 System.out.println("\t[INFO] - Debut testing VALID CHECKSUM DATA : comparaison checksum avec fonction hashage \"MD5\"");

			String checksumServeur = generateChecksum(ReceiveValue);
			String checksumClient = generateChecksum(postValue);
			boolean testCheksum = (checksumClient.equals(checksumServeur));
			if (testCheksum) {
				assertEquals(testCheksum, true);
				System.out.println("\t[INFO] - Cheksum serveur: " + checksumServeur);
				System.out.println("\t[INFO] - Cheksum client: " + checksumClient);
				System.out.println("\t[OK] - Fin testing VALID CHECKSUM DATA OK");
			}
			else {
				System.err.println("\t[INFO] - Cheksum serveur: " + checksumServeur);
				System.err.println("\t[INFO] - Cheksum client: " + checksumClient);
				System.err.println("\t[KO] - Fin testing VALID CHECKSUM DATA KO: Données non compatibles");
				assertEquals(testCheksum, true);
			}

			System.out.println("Output from Server .... ");
			System.out.println(ReceiveValue);
			System.out.println("[OK] - FIN TRAITEMENT TEST POST_REQUEST_OK \n\n");

		}
		catch (Exception e) {
			System.out.println("[KO] - FIN TRAITEMENT TEST POST_REQUEST_KO \n\n");
			
			e.printStackTrace();


		}
		System.out.println("DATA SEND : "+ lis.toString());
		System.out.println("===========================================================================================\n\n");

	}

	private boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		}
		catch (JSONException ex) {
			try {
				new JSONArray(test);
			}
			catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	private String generateChecksum(String stringInpout) {

		String entry = stringInpout;

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(entry.getBytes());

			byte[] mdbytes = md.digest();

			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "HASH FAILED";

	}

}