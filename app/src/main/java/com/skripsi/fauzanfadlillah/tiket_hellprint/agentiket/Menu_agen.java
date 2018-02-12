/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.agentiket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.skripsi.fauzanfadlillah.tiket_hellprint.Info;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.acara.Acara;
import com.skripsi.fauzanfadlillah.tiket_hellprint.prefmanager.PrefManager;
import com.skripsi.fauzanfadlillah.tiket_hellprint.profil.Profil_agen;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Menu_agen extends AppCompatActivity implements View.OnClickListener{
    Button acara,tiket,profil,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_agen);
        acara=(Button)findViewById(R.id.acara);
        tiket=(Button)findViewById(R.id.tiket);
        profil=(Button)findViewById(R.id.profil);
        logout=(Button)findViewById(R.id.logout);

        acara.setOnClickListener(this);
        tiket.setOnClickListener(this);
        profil.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acara:
                Intent a = new Intent(this, Acara.class);
                startActivity(a);
                break;
            case R.id.tiket:
                Intent t = new Intent(this, Menu_tiket.class);
                startActivity(t);
                break;
            case R.id.profil:
                Intent pr = new Intent(this, Profil_agen.class);
                startActivity(pr);
                break;
            case R.id.logout:
                PrefManager prefManager = new PrefManager(getApplicationContext());
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(getApplicationContext(), Login_agen.class));
                finish();
                break;
        }

    }
    /////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.info:
                Intent a = new Intent(Menu_agen.this, Info.class);
                startActivity(a);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}