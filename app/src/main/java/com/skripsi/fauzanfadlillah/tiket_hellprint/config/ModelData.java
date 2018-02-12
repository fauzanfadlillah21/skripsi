/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.config;



public class ModelData {
    String Kategori;
    String Qty;
    String Kantor;
    String Id;
    String Id_kategori;
    String Id_kantor;

    public String getId_kategori() {
        return Id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        Id_kategori = id_kategori;
    }

    public String getId_kantor() {
        return Id_kantor;
    }

    public void setId_kantor(String id_kantor) {
        Id_kantor = id_kantor;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getKantor() {
        return Kantor;
    }

    public void setKantor(String kantor) {
        Kantor = kantor;
    }

    public String getPosisi() {
        return Posisi;
    }

    public void setPosisi(String posisi) {
        Posisi = posisi;
    }

    String Posisi;


}