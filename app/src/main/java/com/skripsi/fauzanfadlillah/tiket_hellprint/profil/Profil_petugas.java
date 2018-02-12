/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.profil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.MyApplication_p;
import com.skripsi.fauzanfadlillah.tiket_hellprint.petugastiket.Login_petugas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Profil_petugas extends AppCompatActivity {
    TextView id, nama, username;
    String user;
    private SharedPreferences prefssatu, prefpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);


        id = (TextView) findViewById(R.id.userid);
        nama = (TextView) findViewById(R.id.nama);
        username = (TextView) findViewById(R.id.username);
        prefssatu = this.getSharedPreferences(
                Login_petugas.SATU_p,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);

//        nama.setText();
        System.out.println(prefssatu.getString(
                Login_petugas.KEY_SATU_p, "NA"));

        user=prefssatu.getString(
                Login_petugas.KEY_SATU_p, "NA");


        loadJson();

    }

    private void loadJson() {
//         user = nama.getText().toString();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST,
                "http://10.42.0.1/tiket/serverandroid/profil_petugas.php?username=" + user, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json_data = response.getJSONObject(i);

                                id.setText(": "+json_data.getString("id"));
                                username.setText(": "+json_data.getString("username"));
                                nama.setText(": "+json_data.getString("nama"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplication(), "Ops..ada kesalahan", Toast.LENGTH_LONG).show();
                    }
                });

        MyApplication_p.getInstance().addToRequestQueue(reqData);
    }

}