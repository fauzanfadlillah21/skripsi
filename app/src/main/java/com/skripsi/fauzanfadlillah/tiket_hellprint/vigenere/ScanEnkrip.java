/*
 * Copyright (c)Fauzan Fadlillah 2017
 */


package com.skripsi.fauzanfadlillah.tiket_hellprint.vigenere;


import java.util.ArrayList;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */

public class ScanEnkrip {

	
	 private int dataPesanInt;
	    private int dataKunciInt;
	    private ArrayList<Integer> listPesan = new ArrayList<Integer>();
	    private ArrayList<Integer> listKunci = new ArrayList<Integer>();
	    
	    public ArrayList<Integer> scannPesan(String pesan, String dataHuruf) {
	        for (int i = 0; i < pesan.length(); i++) {
	            for (int j = 0; j < dataHuruf.length(); j++) {
	                
	                char p = pesan.charAt(i);
	                char dh = dataHuruf.charAt(j);
	                
	                if (p == dh) {
	                    dataPesanInt = dataHuruf.indexOf(dh);
	                    listPesan.add(dataPesanInt);
	                }
	            }
	        }
	        
	        return listPesan;
	    }
	    
	    public ArrayList<Integer> scannKuci(String pesan, String kunci, String dataHuruf) {
	        
	        int jmlKunci = 0;
	        while (kunci.length() < pesan.length()) {
	            kunci += kunci.charAt(jmlKunci);
	            jmlKunci++;
	        }
	        
	        for (int i = 0; i < kunci.length(); i++) {
	            for (int j = 0; j < dataHuruf.length(); j++) {
	                char k = kunci.charAt(i);
	                char dh = dataHuruf.charAt(j);
	                
	                if (k == dh) {
	                    dataKunciInt = dataHuruf.indexOf(dh);
	                    listKunci.add(dataKunciInt);
	                }   
	            }   
	        }
	        return listKunci;
	    }
	
}
