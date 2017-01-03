package com.FuzzerVV.client;

import java.util.ArrayList;
import java.util.List;

public class ListPets {
	public List <Pet> listPets = new ArrayList<Pet>();

	public List <Pet> getListPets() {
		return listPets;
	}

	public void setListPets(List <Pet> listPets) {
		this.listPets = listPets;
	}
	
	public void add(Pet pet){
		listPets.add(pet);
	}
	
	public String toString(){
		String ret="[";
		int i=0;
		for (Pet p : listPets){
			ret+=p.toString();
			if(i<listPets.size()-1)
			{
				ret+=",";
			}
			i++;
		}
		return ret+"]";
	}
}
