/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.config;

public class Category {

	public String getId_tiket() {
		return id_tiket;
	}

	public void setId_tiket(String id_tiket) {
		this.id_tiket = id_tiket;
	}

	private String id_tiket;
	private String name;

	
	public Category(String id_tiket, String name){
		this.id_tiket = id_tiket;
		this.name = name;
	}
	

	
	public void setName(String name){
		this.name = name;
	}

	
	public String getName(){
		return this.name;
	}

}
