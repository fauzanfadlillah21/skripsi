/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.skripsi.fauzanfadlillah.tiket_hellprint.agentiket.Login_agen;
import com.skripsi.fauzanfadlillah.tiket_hellprint.petugastiket.Login_petugas;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */

public class Awal extends AppCompatActivity implements View.OnClickListener {
        Button btnagen, btnptgs;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awal);


        btnagen = (Button) findViewById(R.id.btnloginagen);
        btnptgs = (Button) findViewById(R.id.btnloginpetugas);


        btnagen.setOnClickListener(this);
        btnptgs.setOnClickListener(this);

        }

@Override
public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnloginagen:
        Intent a = new Intent(this, Login_agen.class);
        startActivity(a);
        break;
        case R.id.btnloginpetugas:
        Intent p = new Intent(this, Login_petugas.class);
        startActivity(p);
        break;
        }
        }
}