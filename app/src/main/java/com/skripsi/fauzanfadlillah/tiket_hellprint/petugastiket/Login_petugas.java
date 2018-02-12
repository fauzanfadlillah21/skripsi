/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.petugastiket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.MyApplication_p;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.SessionManager_petugas;
import com.skripsi.fauzanfadlillah.tiket_hellprint.prefmanager.PrefManager_petugas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Login_petugas extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog pd;
    public static final String SATU_p = "PREFS_WORLD_READABLE_WRITABLE_petugas";
    public static final String KEY_SATU_p = "KEY_WORLD_READ_WRITE";
    public static final String Pwd_p = "pass_petugas";
    public static final String key_pwd_p = "key_pass_petugas";
    private SharedPreferences prefssatu;
    private SharedPreferences prefpwd;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    EditText username, password;
    Button login;
    SessionManager_petugas sessionManager;
    private PrefManager_petugas prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_petugas);
        getSupportActionBar().setElevation(0);
        pd = new ProgressDialog(Login_petugas.this);

        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);

        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(this);


        sessionManager = new SessionManager_petugas(getApplicationContext());

        prefManager = new PrefManager_petugas(this);
        if (!prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            Intent i = new Intent(Login_petugas.this, Menu_petugas.class);
            startActivity(i);
            finish();
        }
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            username.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }


        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", username.getText().toString());
            loginPrefsEditor.putString("password", password.getText().toString());
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }


    }


    private void Login() {

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST,
                "http://10.42.0.1/tiket/serverandroid/login_petugas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {


                            JSONObject res = new JSONObject(response);
                            int i;
                            i = res.getInt("success");
                            if (i == 1) {

                                String u, p;
                                u = username.getText().toString();
                                p = password.getText().toString();
                                prefManager.setFirstTimeLaunch(false);

                                sessionManager.createSession(u, p);
                                Intent a = new Intent(Login_petugas.this, Menu_petugas.class);
                                finish();
                                startActivity(a);
                                Toast.makeText(Login_petugas.this, "" + res.getString("message"), Toast.LENGTH_SHORT).show();

                            } else {
                                System.out.println(res.getString("message"));
                                Toast.makeText(Login_petugas.this, "" + res.getString("message"), Toast.LENGTH_SHORT).show();

                            }

//                            Toast.makeText(Tambahpengumuman.this, ""+   res.getString("message") , Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        finish();
//                        startActivity(getIntent());
//                        startActivity( new Intent(Tambah_presensi.this,Tampil_presensi2.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
//                        Toast.makeText(Tambahpengumuman.this, "Gagal kirim noif", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> map = new HashMap<>();
                map.put("username", username.getText().toString());
                map.put("password", password.getText().toString());


                return map;
            }
        };

        MyApplication_p.getInstance().addToRequestQueue(sendData);
    }

    @Override
    public void onClick(View view) {
        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", username.getText().toString());
            loginPrefsEditor.putString("password", password.getText().toString());
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        prefssatu = getSharedPreferences(Login_petugas.SATU_p,
                Context.MODE_PRIVATE + Context.MODE_PRIVATE
                        | Context.MODE_PRIVATE);
        SharedPreferences.Editor worldReadWriteEdit2 = prefssatu.edit();
        worldReadWriteEdit2.putString(Login_petugas.KEY_SATU_p, username.getText()
                .toString());
        worldReadWriteEdit2.commit();

        prefpwd = getSharedPreferences(Login_petugas.Pwd_p,
                Context.MODE_PRIVATE + Context.MODE_PRIVATE
                        | Context.MODE_PRIVATE);
        SharedPreferences.Editor worldReadWriteEdit1 = prefpwd.edit();
        worldReadWriteEdit1.putString(Login_petugas.key_pwd_p, password.getText()
                .toString());
        worldReadWriteEdit1.commit();


        Login();
    }


}
