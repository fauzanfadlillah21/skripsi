/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.agentiket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.isiagen.Tambah_tiket;
import com.skripsi.fauzanfadlillah.tiket_hellprint.tiketterjual.Terjual;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Menu_tiket extends AppCompatActivity implements View.OnClickListener{
Button btntiket,btnjual,btnterjual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_tiket);
        getSupportActionBar().setElevation(0);

        btntiket=(Button)findViewById(R.id.tambahtiket);
        btnjual=(Button)findViewById(R.id.jualtiket);
        btnterjual=(Button)findViewById(R.id.tiketterjual);


        btntiket.setOnClickListener(this);
        btnjual.setOnClickListener(this);
        btnterjual.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tambahtiket:
                Intent t = new Intent(Menu_tiket.this, Tambah_tiket.class);
                startActivity(t);
                break;


            case R.id.jualtiket:
                Intent j = new Intent(Menu_tiket.this, Jual_tiket.class);
                startActivity(j);
                break;
            case R.id.tiketterjual:
                Intent p = new Intent(Menu_tiket.this, Terjual.class);
                startActivity(p);
                break;
        }

    }
}