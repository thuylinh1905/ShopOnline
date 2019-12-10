package com.example.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.ClickListener;
import com.example.shoponline.R;
import com.example.shoponline.model.HoadonChitietMD;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HoadonchitietAdapter extends RecyclerView.Adapter<HoadonchitietAdapter.ViewHolder> {
    Context context;
    ArrayList<HoadonChitietMD> chitietMDS;
    ClickListener clickListener;

    public HoadonchitietAdapter(Context context, ArrayList<HoadonChitietMD> chitietMDS, ClickListener clickListener) {
        this.context = context;
        this.chitietMDS = chitietMDS;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HoadonchitietAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadonct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoadonchitietAdapter.ViewHolder holder, final int position) {
        HoadonChitietMD hoadonChitietMD = chitietMDS.get(position);
        holder.txt_tendon.setText(hoadonChitietMD.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_giadon.setText(decimalFormat.format(hoadonChitietMD.getGiasanpham()) + "VND");
        Picasso.with(context).load(hoadonChitietMD.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgdonhang);
        holder.btn_chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(position);
            }
        });
        holder.btn_huydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick2(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chitietMDS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tendon, txt_giadon;
        Button btn_huydon, btn_chitiet;
        ImageView imgdonhang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_tendon = itemView.findViewById(R.id.txt_tenDon);
            txt_giadon = itemView.findViewById(R.id.txt_GiaDonhang);
            btn_huydon = itemView.findViewById(R.id.btn_huydon);
            btn_chitiet = itemView.findViewById(R.id.btn_chitiet);
            imgdonhang = itemView.findViewById(R.id.imganhdonhang);
        }
    }
}
