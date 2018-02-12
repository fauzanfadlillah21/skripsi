/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.acara;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.config.MyApplication_p;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Acara extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFishPrice;
    private EditText kodetahun, mhsw;
    TextView ambiltahun, ambilmhsw;
    Button btnlihat;
    LinearLayout mainLayout;
    String kode1, kode2;
    private TextView infonilai, pengumuman;
    private int userchoice;
    List<Model_acara> mItems;

    private LinearLayoutManager linearLayoutManager;

    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private String source, jml;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.p_acara);

        pengumuman = (TextView) findViewById(R.id.pengumuman);


        mRecyclerview = (RecyclerView) findViewById(R.id.acara);

        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(Acara.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new Adapter_acara(Acara.this, mItems);
        mRecyclerview.setAdapter(mAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.a, R.color.b, R.color.c, R.color.d);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        loadJson();

                                    }
                                }
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click

        switch (item.getItemId()) {

            case R.id.refresh:
                mItems.clear();
                mRecyclerview.getAdapter().notifyDataSetChanged();
                loadJson();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadJson() {
        swipeRefreshLayout.setRefreshing(true);

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST,
                "http://10.42.0.1/tiket/serverandroid/list_acara.php", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        swipeRefreshLayout.setRefreshing(false);
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json_data = response.getJSONObject(i);
                                if (json_data.getString("data") == "kosong" || json_data.getString("data").equals("kosong")) {
                                    pengumuman.setVisibility(View.VISIBLE);
                                } else {
                                    pengumuman.setVisibility(View.GONE);
                                    Model_acara isidata = new Model_acara();
                                    isidata.setJudul(json_data.getString("judul"));
                                    isidata.setIsi(json_data.getString("isi"));
                                    isidata.setTanggal(json_data.getString("tanggal"));



                                    mItems.add(isidata);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplication(), "Ops..ada kesalahan", Toast.LENGTH_LONG).show();
                    }
                });

        MyApplication_p.getInstance().addToRequestQueue(reqData);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

    }
}