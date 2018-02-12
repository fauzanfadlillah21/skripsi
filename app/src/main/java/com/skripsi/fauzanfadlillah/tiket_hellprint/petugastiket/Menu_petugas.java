/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.petugastiket;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.fauzanfadlillah.tiket_hellprint.Info;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.acara.Acara;
import com.skripsi.fauzanfadlillah.tiket_hellprint.agentiket.Login_agen;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.MyApplication_p;
import com.skripsi.fauzanfadlillah.tiket_hellprint.prefmanager.PrefManager_petugas;
import com.skripsi.fauzanfadlillah.tiket_hellprint.profil.Profil_petugas;
import com.skripsi.fauzanfadlillah.tiket_hellprint.vigenere.EnkripDekrip;
import com.skripsi.fauzanfadlillah.tiket_hellprint.vigenere.ScanEnkrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Menu_petugas extends AppCompatActivity  implements View.OnClickListener{
    Button acara,tiket,profil,logout;
    private String kode1, kode2, kode3, dataHuruf1,ambilenkrip,ambildataenkrip;
    String jumlah;
    ProgressDialog pd;
    public static final int datasudahupdate = 1;
    public static final int update = 3;
    public static final int datatidakada = 5;
    private SharedPreferences prefssatu, prefpassword;
    String user;
    private  String isi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_petugas);
        pd = new ProgressDialog(Menu_petugas.this);
        acara=(Button)findViewById(R.id.acara);
        tiket=(Button)findViewById(R.id.tiket);
        profil=(Button)findViewById(R.id.profil);
        logout=(Button)findViewById(R.id.logout);

        acara.setOnClickListener(this);
        tiket.setOnClickListener(this);
        profil.setOnClickListener(this);
        logout.setOnClickListener(this);

        prefssatu = this.getSharedPreferences(
                Login_agen.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);


        user = prefssatu.getString(
                Login_agen.KEY_SATU, "NA");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acara:
                Intent a = new Intent(this, Acara.class);
                startActivity(a);
                break;
            case R.id.tiket:
                try {

                    Intent intent = new Intent(
                            "com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
                    startActivityForResult(intent, 0);


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR:", Toast.LENGTH_SHORT).show();

                }





                break;
            case R.id.profil:
                Intent pr = new Intent(this, Profil_petugas.class);
                startActivity(pr);
                break;
            case R.id.logout:
                PrefManager_petugas prefManager = new PrefManager_petugas(getApplicationContext());
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(getApplicationContext(), Login_petugas.class));
                finish();
                break;
        }
    }


    // In the same activity you�ll need the following to retrieve the results:
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {

//                tvResult.setText(intent.getStringExtra("SCAN_RESULT"));

//                ambilkey = tvResult.getText().toString();
//                new Ambilkey().execute();

                System.out.println(intent.getStringExtra("SCAN_RESULT"));
                ambildataenkrip=intent.getStringExtra("SCAN_RESULT");









                String NULL = "";

                if (ambildataenkrip.equals(NULL)) {
                    kode3 = "11111111";
                    Toast.makeText(getApplicationContext(), "Data Qr Code Mungkin Salah", Toast.LENGTH_LONG).show();

                } else {


                    dataHuruf1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890,.@";
                    String pesan1 = ambildataenkrip;
                    String kunci1 = "tugasakhir";

                    ArrayList<Integer> de = new ArrayList<Integer>();

                    ScanEnkrip s2 = new ScanEnkrip();
                    ArrayList<Integer> sC = s2.scannPesan(pesan1, dataHuruf1);
                    ArrayList<Integer> sK2 = s2.scannKuci(pesan1, kunci1, dataHuruf1);

                    System.out.println("\nDekripsi");
                    EnkripDekrip ed2 = new EnkripDekrip();
                    for (int i = 0; i < pesan1.length(); i++) {
                        int iC = Integer.parseInt(sC.get(i).toString());
                        int iK2 = Integer.parseInt(sK2.get(i).toString());
                        int gDE = ed2.getDekrip(iC, iK2, dataHuruf1);

                        de.add(gDE);
                    }

                    String hDE = ed2.getHasil(de, dataHuruf1);

                    jumlah=hDE;




                }


                dekrip();

            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("cancel");
            }


        }

    }

    private void dekrip() {

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST,
                "http://10.42.0.1/tiket/serverandroid/dekrip.php?enkrip="+jumlah, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json_data = response.getJSONObject(i);

                                int a;
                                a = json_data.getInt("data");

                                if (a==0){
                                    showDialog(datatidakada);
                                }else if (a==1){
                                    infonama();
                                }else if (a==2){
                                    showDialog(datasudahupdate);
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(getApplication(), "Ops..ada kesalahan", Toast.LENGTH_LONG).show();
                    }
                });

        MyApplication_p.getInstance().addToRequestQueue(reqData);
    }
    private void infonama() {

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST,
                "http://10.42.0.1/tiket/serverandroid/infonama.php?idtiket="+jumlah, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json_data = response.getJSONObject(i);
                                isi=json_data.getString("data");

                                showDialog(update);





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(getApplication(), "Ops..ada kesalahan", Toast.LENGTH_LONG).show();
                    }
                });

        MyApplication_p.getInstance().addToRequestQueue(reqData);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {

            case datatidakada:
                android.support.v7.app.AlertDialog.Builder errorDialog1 = new android.support.v7.app.AlertDialog.Builder(this);
                errorDialog1.setTitle("INFO TIKET");
                errorDialog1.setMessage("TIKET SALAH / TIKET TIDAK TERDAFTAR!!! ");
                errorDialog1.setNegativeButton("OKE",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                android.support.v7.app.AlertDialog errorAlert1 = errorDialog1.create();
                return errorAlert1;
            case update:
                System.out.println(isi);
                android.support.v7.app.AlertDialog.Builder errorDialog3 = new android.support.v7.app.AlertDialog.Builder(this);
                errorDialog3.setTitle("INFO TIKET");
                errorDialog3.setMessage("Info Akun :"+isi+"\nApakah anda ingin update tiket?");
                // Setting Positive "Yes" Button
                errorDialog3.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.cancel();
                        Update();


                    }
                });


                errorDialog3.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                android.support.v7.app.AlertDialog errorAlert3 = errorDialog3.create();
                return errorAlert3;
            case datasudahupdate:
                android.support.v7.app.AlertDialog.Builder errorDialog4 = new android.support.v7.app.AlertDialog.Builder(this);
                errorDialog4.setTitle("INFO TIKET");
                errorDialog4.setMessage("Tiket sudah dipakai !!");

                errorDialog4.setPositiveButton("OKE",
                        // errorDialog.setNeutralButton("Lanjut",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                android.support.v7.app.AlertDialog errorAlert4 = errorDialog4.create();
                return errorAlert4;



        }


        return dialog;
    }

    private void Update() {

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, "http://10.42.0.1/tiket/serverandroid/update.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(Menu_petugas.this, "Tiket berhasil diupdate" , Toast.LENGTH_SHORT).show();




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
                map.put("idtiket", jumlah);



                return map;
            }
        };

        MyApplication_p.getInstance().addToRequestQueue(sendData);
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
                Intent a = new Intent(Menu_petugas.this, Info.class);
                startActivity(a);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}