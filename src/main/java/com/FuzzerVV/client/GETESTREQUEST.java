package com.FuzzerVV.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class GETESTREQUEST {

	List<Pet> listPet = new ArrayList<Pet>();
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String BASE_URL = "http://localhost:8080";

	// public static RepeatedTest suite() {
	// return new RepeatedTest(new TestSuite(JerseyClientTest.class), 10);
	// }

	@Test
	// @Repeat(10)
	public void GetRequestsOK(Map.Entry<String, Path> entry) throws IOException {
		
		System.out.println("===========================================================================================\n");
		try {
			Swagger swagger = new SwaggerParser().read("swagger.json");

			String FullUrl=BASE_URL+swagger.getBasePath()+entry.getKey();
			if (FullUrl.contains("{id}")){
				FullUrl = FullUrl.substring(0, FullUrl.length()-4);
			}
			System.out.println("[INFO] - DEBUT TRAITEMENT TEST GET_REQUEST_OK");
			Client client = Client.create();

			WebResource webResource = client.resource(FullUrl);

			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			String output = response.getEntity(String.class);
			
			Operation getGet = swagger.getPaths().get(entry.getKey()).getGet();
			
			
			int status = response.getStatus();
			System.out.println("\t[INFO] - Debut testing STATUS CODE");
			if (status != 200) {
				Response test = getGet.getResponses().get(status);
				if (test != null){
					throw new Exception("\t[KO] - erreur serveur: erreur " + test.getDescription());
				}
				else{
					throw new Exception("\t[KO] - erreur serveur: SWAGGER DOESN'T SUPPORT THIS STATUS");
				}
				/*
				if (status == 401) {
					getGet.getResponses().get(status);
					if 
					throw new Exception("\t[KO] - Utilisateur non authentifié: erreur " + status);
				}
				else if (status == 403) {
					throw new Exception("\t[KO] - Accés refusé: erreur " + status);
				}
				else if (status == 404) {
					throw new Exception("\t[KO] - Page non trouvée: erreur " + status);
				}
				else if (status == 500 || status == 503) {
					throw new Exception("\t[KO] - erreur serveur: erreur " + status);
				}
				else {
					throw new Exception("\t[KO] - erreur " + status);
				}
				*/
			}
			assertEquals(status, 200);
			System.out.println("\t[OK] - fin testing STATUS CODE OK : " + status);

			System.out.println("\t[INFO] - Debut testing DATA Consumed Type");
			String consumedData="["+response.getType().getType()+"/"+response.getType().getSubtype()+"]";
			boolean testDataConsumedType = swagger.getConsumes().toString().equals(consumedData);
	
			if (!testDataConsumedType) {
				System.err.println("\t[KO] - fin testing DATA Consumed Type : " + consumedData);
				assertEquals(testDataConsumedType, false);
			}
			else {
				System.out.println("\t[OK] - fin testing DATA Consumed Type : " + consumedData);
				assertEquals(testDataConsumedType, true);
			}

			System.out.println("\t[INFO] - Debut testing DATA Produced TYPE");
			String producedData="["+response.getType().getType()+"/"+response.getType().getSubtype()+"]";
			boolean testDataProduceType = swagger.getProduces().toString().equals(producedData);
			if (!testDataProduceType) {
				System.err.println("\t[KO] - fin testing DATA Produced TYPE KO : " + producedData);
				assertEquals(testDataProduceType, false);
			}
			else {
				assertEquals(testDataProduceType, true);
				System.out.println("\t[OK] - fin testing DATA Produced TYPE OK : " + producedData);
			}
			
			
			
			if (output == null) {
				throw new Exception("\t[KO] - Fin testing : Donnée reçus null");
			}
			System.out.println("\t[INFO] - Debut testing VALID JSON DATA");
			boolean testFormatJson = isJSONValid(output);
			if (testFormatJson) {
				assertEquals(testFormatJson, true);
				System.out.println("\t[OK] - Fin testing VALID JSON DATA");
			}
			else {
				System.err.println("\t[KO] - Format données Json non valid");
				assertEquals(testFormatJson, true);
			}

			System.out.println("\t[INFO] - Debut testing VALID CHECKSUM DATA : comparaison checksum avec fonction hashage \"MD5\"");
			
			 ListPets listPets = new ListPets();
			listPets.add( new Pet(0, "othmane","SAN"));
			listPets.add( new Pet(1, "ilyass","AZER"));
			listPets.add( new Pet(2, "Ridoux","Olov"));
			
			String compare = output.replaceAll("\n", "");
			compare = compare.replaceAll(" ", "");
			
			String checksumServeur = generateChecksum(compare);
			String checksumClient = generateChecksum(listPets.toString());
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
			System.out.println("\t[INFO] - Output from Server .... ");
			System.out.println("\t[RESULT] - " + output);
			System.out.println("[OK] - FIN TRAITEMENT TEST_GET_REQUEST_OK ");

		
		}
		catch (Exception e) {
			System.out.println("[KO] - FIN TRAITEMENT TEST GET_REQUEST_KO \n\n");
			e.printStackTrace();

		}
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