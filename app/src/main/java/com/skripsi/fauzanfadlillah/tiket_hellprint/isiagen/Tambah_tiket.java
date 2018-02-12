/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.isiagen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.agentiket.Login_agen;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.MyApplication_p;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Tambah_tiket extends AppCompatActivity implements View.OnClickListener{
    ProgressDialog pd;
    EditText no,namaacara,agen,idtiket,jenistiket;
    Button simpan;
    private SharedPreferences prefssatu, prefpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambahtiket);
        pd = new ProgressDialog(Tambah_tiket.this);
        no=(EditText)findViewById(R.id.no);
        namaacara=(EditText)findViewById(R.id.namaacara);
        agen=(EditText)findViewById(R.id.agen);
        idtiket=(EditText)findViewById(R.id.idtiket);


        jenistiket=(EditText)findViewById(R.id.jenistiket);
        prefssatu = this.getSharedPreferences(
                Login_agen.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);


        agen.setText( prefssatu.getString(
                Login_agen.KEY_SATU, "NA"));
        simpan=(Button)findViewById(R.id.simpan);


        simpan.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        simpan();
    }

    private void simpan() {

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, "http://10.42.0.1/tiket/serverandroid/tambahtiket.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(Tambah_tiket.this, "Berhasil disimpan" , Toast.LENGTH_SHORT).show();

                            no.setText("");
                            namaacara.setText("");
                            agen.setText("");
                            idtiket.setText("");
                            jenistiket.setText("");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> map = new HashMap<>();
                map.put("no", no.getText().toString());
                map.put("namaacara", namaacara.getText().toString());
                map.put("agen", agen.getText().toString());
                map.put("idtiket", idtiket.getText().toString());
                map.put("jenistiket", jenistiket.getText().toString());


                return map;
            }
        };

        MyApplication_p.getInstance().addToRequestQueue(sendData);
    }
}
