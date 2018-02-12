/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.agentiket;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.MyApplication_p;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.SessionManager_agen;
import com.skripsi.fauzanfadlillah.tiket_hellprint.prefmanager.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Login_agen extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog pd;
    public static final String SATU = "PREFS_WORLD_READABLE_WRITABLE";
    public static final String KEY_SATU = "KEY_WORLD_READ_WRITE";
    public static final String Pwd = "pass";
    public static final String key_pwd = "key_pass";
    private SharedPreferences prefssatu;
    private SharedPreferences prefpwd;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    static final int tampil_error = 1;
    static final int regg = 2;

    EditText username, password;
    Button login;
    SessionManager_agen sessionManager;
    private PrefManager prefManager;


    ///notif
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    TextView txtRegId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_agen);
        getSupportActionBar().setElevation(0);
        pd = new ProgressDialog(Login_agen.this);

        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);

        login = (Button) findViewById(R.id.btn_login);
        txtRegId = (TextView) findViewById(R.id.txtregid);

        login.setOnClickListener(this);


        sessionManager = new SessionManager_agen(getApplicationContext());

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            Intent i = new Intent(Login_agen.this, Menu_agen.class);
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
                "http://10.42.0.1/tiket/serverandroid/login_agen.php",
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
                                Intent a = new Intent(Login_agen.this, Menu_agen.class);
                                finish();
                                startActivity(a);
                                Toast.makeText(Login_agen.this, "" + res.getString("message"), Toast.LENGTH_SHORT).show();

                            } else {
                                System.out.println(res.getString("message"));
                                Toast.makeText(Login_agen.this, "" + res.getString("message"), Toast.LENGTH_SHORT).show();

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
                map.put("Device", txtRegId.getText().toString());


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

        prefssatu = getSharedPreferences(Login_agen.SATU,
                Context.MODE_PRIVATE + Context.MODE_PRIVATE
                        | Context.MODE_PRIVATE);
        SharedPreferences.Editor worldReadWriteEdit2 = prefssatu.edit();
        worldReadWriteEdit2.putString(Login_agen.KEY_SATU, username.getText()
                .toString());
        worldReadWriteEdit2.commit();

        prefpwd = getSharedPreferences(Login_agen.Pwd,
                Context.MODE_PRIVATE + Context.MODE_PRIVATE
                        | Context.MODE_PRIVATE);
        SharedPreferences.Editor worldReadWriteEdit1 = prefpwd.edit();
        worldReadWriteEdit1.putString(Login_agen.key_pwd, password.getText()
                .toString());
        worldReadWriteEdit1.commit();


        Login();
    }


}
