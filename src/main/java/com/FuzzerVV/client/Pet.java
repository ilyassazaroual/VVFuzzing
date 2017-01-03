package com.FuzzerVV.client;

public class Pet {

	private int id;

	private String name;

	private String tag;

	public Pet(int id, String name, String tag) {
		this.id = id;
		this.name = name;
		this.tag = tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String toString(){
		String ret="{\"name\":\""+name+"\",\"id\":"+id+",\"tag\":\""+tag+"\"}";
		return ret;
	}

}
