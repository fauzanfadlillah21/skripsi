/*
 * Copyright (c)Fauzan Fadlillah 2017
 */


package com.skripsi.fauzanfadlillah.tiket_hellprint.vigenere;

import java.util.ArrayList;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */

public class EnkripDekrip {

	private int chiper;
    private int pesan;
    private String hasil = "";

    public int getEnkrip(int pesan, int kunci, String dataHuruf) {

        int c = pesan + kunci;

        if (c < dataHuruf.length()) {
            chiper = c;
        } else {
            chiper = c - dataHuruf.length();
        }

        return chiper;
    }

    public int getDekrip(int chiper, int kunci, String dataHuruf) {
        int p = chiper - kunci;
        if (p >= 0) {
            pesan = p;
        } else {
            pesan = p + dataHuruf.length();
        }
        return pesan;
    }

    public String getHasil(ArrayList<Integer> intEnkrip, String dataHuruf) {

        for (int i = 0; i < intEnkrip.size(); i++) {
            int a = Integer.parseInt(intEnkrip.get(i).toString());
            char z = dataHuruf.charAt(a);
            hasil += z;
        }

        return hasil;
    }
	
}

