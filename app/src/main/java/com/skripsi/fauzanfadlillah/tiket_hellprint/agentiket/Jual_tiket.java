/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.agentiket;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.Category;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.MyApplication_p;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Jual_tiket extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog pd;
    EditText nama, ktp, tiket2, alamat;
    RadioGroup kelamin;
    Button simpan;
    private SharedPreferences prefssatu, prefpassword;

    private ArrayList<Category> listtiket;
    private Spinner spinnertiket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jual_tiket);

        pd = new ProgressDialog(Jual_tiket.this);
        nama = (EditText) findViewById(R.id.nama);
        ktp = (EditText) findViewById(R.id.noktp);
        tiket2 = (EditText) findViewById(R.id.idtiket);
        alamat = (EditText) findViewById(R.id.alamat);

        kelamin = (RadioGroup) findViewById(R.id.gender_radio_group);

        spinnertiket = (Spinner) findViewById(R.id.spinidtiket);
        listtiket = new ArrayList<Category>();


        simpan = (Button) findViewById(R.id.simpan);


        simpan.setOnClickListener(this);

        Tiket();


    }

    @Override
    public void onClick(View view) {
        simpan();
    }


    private void spinnertiket() {
        final List<String> tiket = new ArrayList<String>();
        tiket.add("pilih-tiket");
        for (int i = 0; i < listtiket.size(); i++) {

            tiket.add(listtiket.get(i).getName());

        }
        ArrayAdapter<String> spinnerAdaptertahun = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.spinner_item, tiket);
//        spinnerAdaptertahun.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerAdaptertahun.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnertiket.setAdapter(spinnerAdaptertahun);
        spinnertiket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg3, View arg0,
                                       int position, long arg1) {

                String thn = (String) (spinnertiket.getSelectedItem());

                String[] split =thn.split("-");
                String idtiket = split[0];
                tiket2.setText(idtiket);



                System.out.println(thn);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

    }


    private void Tiket() {
        pd.setMessage("Mengambil tiket.. \nMohon Tunggu.!!");
        pd.setCancelable(false);
        pd.show();


        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST,
                "http://10.42.0.1/tiket/serverandroid/idtiket.php", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                Category cat = new Category(data.getString("id_tiket"),
                                        data.getString("id_tiket"));
                                data.getString("id_tiket");


                                listtiket.add(cat);


                            } catch (JSONException e) {
                                e.printStackTrace();


                            }
                        }


                        spinnertiket();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
//                        Toast.makeText(getApplication(), "Ada Kesalahan Mohon Periksa Kembali", Toast.LENGTH_LONG).show();
                    }
                });

        MyApplication_p.getInstance().addToRequestQueue(reqData);
    }


    private void simpan() {

        int selectId = kelamin.getCheckedRadioButtonId();
        final String kelamin;
        if(selectId == R.id.femalerb)kelamin = "Perempuan";
        else kelamin = "Laki-Laki";

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, "http://10.42.0.1/tiket/serverandroid/tambahjual.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(Jual_tiket.this, res.getString("message"), Toast.LENGTH_SHORT).show();

                            nama.setText("");
                            ktp.setText("");
                            tiket2.setText("");
                            alamat.setText("");


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
                map.put("tiket", tiket2.getText().toString());
                map.put("noktp", ktp.getText().toString());
                map.put("nama", nama.getText().toString());
                map.put("alamat", alamat.getText().toString());
                map.put("jenis", kelamin.toString());

                return map;
            }
        };

        MyApplication_p.getInstance().addToRequestQueue(sendData);
    }

}