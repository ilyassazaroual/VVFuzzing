package com.mkyong.rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import com.google.gson.reflect.TypeToken;
//import com.mkyong.Track;

@Path("/json/metallica")
public class JSONService {

//	List<Track> listTrack = new ArrayList<Track>();

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTrackInJSON(@QueryParam("listTrackServeur") String ListTrackServeur) {
		return ListTrackServeur;

//		Track track = new Track();

		//track.setTitle("Enter Sandman");
		//track.setSinger("Metallica");
		// Connection conn = null;
		// conn = connectToDatabaseOrDie();
		// populateListOfTopics(conn, listTrack);
		//return track.toString();

	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(String track) {

		// TypeToken<List<Track>> token = new TypeToken<List<Track>>() {
		// };
		// Gson a = new Gson();
		// List<Track> personList = a.fromJson(track, token.getType());
		String result = "Post response: " + track;
		return Response.status(201).entity(result).build();

	}

	// private Connection connectToDatabaseOrDie() {
	// Connection conn = null;
	// try {
	// Class.forName("org.mysql.jdbc.Driver");
	// String url = "jdbc:postgresql://localhost:5432/VV";
	// conn = DriverManager.getConnection(url, "postgres", "deserderde");
	// }
	// catch (SQLException e) {
	// e.printStackTrace();
	// System.exit(2);
	// }
	// catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// }
	// return conn;
	// }
	//
	// private void populateListOfTopics( Connection conn,
	// List<Track> listTrack) {
	// try {
	// Statement st = conn.createStatement();
	// ResultSet rs = st.executeQuery("SELECT title, singer FROM TRACK ORDER BY id");
	// while (rs.next()) {
	// Track track = new Track();
	// // track.id = rs.getInt ("id");
	// track.setTitle(rs.getString("title"));
	// track.setSinger(rs.getString("singer"));
	// listTrack.add(track);
	// }
	// rs.close();
	// st.close();
	// }
	// catch (SQLException se) {
	// System.err.println("Threw a SQLException creating the list of blogs.");
	// System.err.println(se.getMessage());
	// }
	// }
}