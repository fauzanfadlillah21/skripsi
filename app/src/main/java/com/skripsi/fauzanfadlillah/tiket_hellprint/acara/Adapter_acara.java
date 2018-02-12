/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.acara;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.fauzanfadlillah.tiket_hellprint.R;

import java.util.List;

/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Adapter_acara extends RecyclerView.Adapter<Adapter_acara.HolderData> {
    private List<Model_acara> mItems;
    private Context context;

    public Adapter_acara(Context context, List<Model_acara> items) {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public Adapter_acara.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_acara, parent, false);
        Adapter_acara.HolderData holderData = new Adapter_acara.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(Adapter_acara.HolderData holder, int position) {
        Model_acara md = mItems.get(position);

        holder.judul.setText(md.getJudul());
        holder.tanggal.setText(md.getTanggal());
        holder.isi.setText(md.getIsi());




        holder.md = md;


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {
        ImageView foto;
        private TextView judul,tanggal,isi;

        Model_acara md;

        public HolderData(View view) {
            super(view);

            judul = (TextView) view.findViewById(R.id.tvJudul);
            tanggal = (TextView) view.findViewById(R.id.tvWaktu);
            isi = (TextView) view.findViewById(R.id.isi);

        }
    }
}