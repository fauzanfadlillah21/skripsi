/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package com.skripsi.fauzanfadlillah.tiket_hellprint.tiketterjual;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.fauzanfadlillah.tiket_hellprint.R;
import com.skripsi.fauzanfadlillah.tiket_hellprint.cetakqrcode.Cetak_Qrcode;

import java.util.List;


/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */


public class Adapter_terjual extends RecyclerView.Adapter<Adapter_terjual.HolderData> {
    private List<Model_terjual> mItems;
    private Context context;

    public Adapter_terjual(Context context, List<Model_terjual> items) {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public Adapter_terjual.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_acara, parent, false);
        Adapter_terjual.HolderData holderData = new Adapter_terjual.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(Adapter_terjual.HolderData holder, int position) {
        Model_terjual md = mItems.get(position);

        holder.judul.setText(md.getJudul());
        holder.tanggal.setText(md.getTanggal());
        holder.isi.setText(md.getIsi());
        holder.enkripsi.setText(md.getEnkripsi());

        holder.md = md;


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {
        ImageView foto;
        private TextView judul,tanggal,isi,enkripsi;

        Model_terjual md;
        CardView card;
        public HolderData(View view) {
            super(view);

            judul = (TextView) view.findViewById(R.id.tvJudul);
            tanggal = (TextView) view.findViewById(R.id.tvWaktu);
            isi = (TextView) view.findViewById(R.id.isi);
            enkripsi = (TextView) view.findViewById(R.id.enkripsi);
            card=(CardView)view.findViewById(R.id.cardview);




            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, Cetak_Qrcode.class);
                    update.putExtra("update",1);
                    update.putExtra("enkripsi",md.getEnkripsi());
                    context.startActivity(update);
                }
            });
        }

    }
}