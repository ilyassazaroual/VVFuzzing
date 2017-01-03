package com.FuzzerVV.client;
//package com.FuzzerVV.client;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Test;
//
//import com.mkyong.Track;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//
//public class JerseyClientTest {
//
//	List<Pet> listPet = new ArrayList<Pet>();
//	public static final String ANSI_GREEN = "\u001B[32m";
//	public static final String ANSI_RESET = "\u001B[0m";
//
//	public List<Track> listTrackClient = new ArrayList<Track>();
//	public List<Track> listTrackServeur = new ArrayList<Track>();
//
//	// public static RepeatedTest suite() {
//	// return new RepeatedTest(new TestSuite(JerseyClientTest.class), 10);
//	// }
//
//	@Test
//	// @Repeat(10)
//	public void GetRequestsOK() throws IOException {
//		System.out.println("===========================================================================================");
//		try {
//			System.out.println("[INFO] - DEBUT TRAITEMENT TEST GET_REQUEST_OK");
//			Client client = Client.create();
//
//			WebResource webResource = client.resource("http://localhost:8080/RESTSwaggerTest/rest/json/metallica/get").queryParam("listTrackServeur", listTrackServeur.toString());
//
//			ClientResponse response = webResource.accept("application/json")/* .header("listTrack", listTrack) */.get(ClientResponse.class);
//
//			int status = response.getStatus();
//			System.out.println("\t[INFO] - Debut testing STATUS CODE");
//			if (status != 200) {
//				if (status == 401) {
//					System.err.println("\t[KO] - Utilisateur non authentifié: erreur " + status);
//				}
//				else if (status == 403) {
//					System.err.println("\t[KO] - Accés refusé: erreur " + status);
//				}
//				else if (status == 404) {
//					System.err.println("\t[KO] - Page non trouvée: erreur " + status);
//				}
//				else if (status == 500 || status == 503) {
//					System.err.println("\t[KO] - erreur serveur: erreur " + status);
//				}
//				else {
//					System.err.println("\t[KO] - erreur " + status);
//				}
//			}
//			assertEquals(status, 200);
//			System.out.println("\t[OK] - fin testing STATUS CODE OK : " + status);
//
//			System.out.println("\t[INFO] - Debut testing DATA SUBTYPE");
//			boolean testDataSubType = "json".equals(response.getType().getSubtype());
//			if (!testDataSubType) {
//				System.err.println("\t[KO] - fin testing DATA SUBTYPE KO : " + response.getType().getSubtype());
//				assertEquals(testDataSubType, true);
//			}
//			else {
//				System.out.println("\t[OK] - fin testing DATA SUBTYPE OK : " + response.getType().getSubtype());
//				assertEquals(testDataSubType, true);
//			}
//
//			System.out.println("\t[INFO] - Debut testing DATA TYPE");
//			boolean testDataType = "application".equals(response.getType().getType());
//			if (!testDataType) {
//				System.err.println("\t[KO] - fin testing DATA TYPE KO : " + response.getType().getType());
//			}
//			else {
//				assertEquals(testDataSubType, true);
//				System.out.println("\t[OK] - fin testing DATA TYPE OK : " + response.getType().getType());
//			}
//			String output = response.getEntity(String.class);
//
//			System.out.println("\t[INFO] - Debut testing VALID JSON DATA");
//			boolean testFormatJson = isJSONValid(output);
//			if (testFormatJson) {
//				assertEquals(testFormatJson, true);
//				System.out.println("\t[OK] - Fin testing VALID JSON DATA");
//			}
//			else {
//				System.err.println("\t[KO] - Format données Json non valid");
//			}
//
//			System.out.println("\t[INFO] - Debut testing VALID CHECKSUM DATA : comparaison checksum avec fonction hashage \"MD5\"");
//			String checksumServeur = generateChecksum(output);
//			Track track = new Track();
//			track.setTitle("Enter Sandman");
//			track.setSinger("Metallica");
//			listTrackClient.add(track);
//			String checksumClient = generateChecksum(listTrackClient.toString());
//			boolean testCheksum = (checksumClient.equals(checksumServeur));
//			if (testCheksum) {
//				assertEquals(testCheksum, true);
//				System.out.println("\t[INFO] - Cheksum serveur: " + checksumServeur);
//				System.out.println("\t[INFO] - Cheksum client: " + checksumClient);
//				System.out.println("\t[OK] - Fin testing VALID CHECKSUM DATA OK");
//			}
//			else {
//				System.err.println("\t[INFO] - Cheksum serveur: " + checksumServeur);
//				System.err.println("\t[INFO] - Cheksum client: " + checksumClient);
//				System.err.println("\t[KO] - Fin testing VALID CHECKSUM DATA KO: Données non compatibles");
//			}
//
//			System.out.println("\t[INFO] - Output from Server .... ");
//			System.out.println("\t[RESULT] - " + output);
//			System.out.println("[OK] - FIN TRAITEMENT TEST_GET_REQUEST_OK ");
//
//		}
//		catch (Exception e) {
//			System.out.println("[KO] - FIN TRAITEMENT TEST GET_REQUEST_KO \n\n");
//			e.printStackTrace();
//
//		}
//		System.out.println("===========================================================================================\n\n");
//	}
//
//	@Test
//	public void PostRequestsOK() {
//
//		try {
//			System.out.println("[INFO] - DEBUT TRAITEMENT TEST POST_REQUEST_OK ");
//			Client client = Client.create();
//
//			WebResource webResource = client.resource("http://localhost:8080/RESTSwaggerTest/rest/json/metallica/post");
//
//			String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
//
//			ClientResponse response = webResource.type("application/json")/* header("listTrack", listTrack) */.post(ClientResponse.class, input);
//
//			if (response.getStatus() != 201) {
//				throw new RuntimeException("[KO] - Failed : HTTP error code : " + response.getStatus());
//			}
//
//			System.out.println("Output from Server .... ");
//			String output = response.getEntity(String.class);
//			System.out.println(output);
//
//		}
//		catch (Exception e) {
//
//			System.out.println("[KO] - FIN TRAITEMENT TEST POST_REQUEST_KO \n\n");
//			e.printStackTrace();
//
//		}
//
//		System.out.println("[OK] - FIN TRAITEMENT TEST POST_REQUEST_OK \n\n");
//
//	}
//
//	@Test
//	public void MultipleGetRequest() {
//		System.err.println("===========================================================================================");
//		System.out.println("[INFO] - DEBUT TEST MONTEE EN CHARGE GET ");
//		for (int i = 0; i < 5; i++) {
//			System.out.println("[INFO] - test GET N* " + (i + 1));
//			try {
//				GetRequestsOK();
//			}
//			catch (IOException e) {
//				System.err.println("[KO] - FIN TEST MONTEE EN CHARGE GET KO ");
//				e.printStackTrace();
//			}
//		}
//		System.out.println("[OK] - FIN TEST MONTEE EN CHARGE GET OK ");
//		System.err.println("===========================================================================================\n\n");
//	}
//
//	private boolean isJSONValid(String test) {
//		try {
//			new JSONObject(test);
//		}
//		catch (JSONException ex) {
//			try {
//				new JSONArray(test);
//			}
//			catch (JSONException ex1) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private String generateChecksum(String stringInpout) {
//
//		String entry = stringInpout;
//
//		MessageDigest md;
//		try {
//			md = MessageDigest.getInstance("MD5");
//			md.update(entry.getBytes());
//
//			byte[] mdbytes = md.digest();
//
//			StringBuffer sb = new StringBuffer("");
//			for (int i = 0; i < mdbytes.length; i++) {
//				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
//			}
//
//			return sb.toString();
//		}
//		catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//
//		return "HASH FAILED";
//
//	}
//
//}