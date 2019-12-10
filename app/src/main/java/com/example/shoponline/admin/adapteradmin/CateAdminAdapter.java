package com.example.shoponline.admin.adapteradmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.ClickListener;
import com.example.shoponline.R;
import com.example.shoponline.adapter.categoryAdapter;
import com.example.shoponline.admin.CategoryAdmin;
import com.example.shoponline.model.danhmucMD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CateAdminAdapter extends RecyclerView.Adapter<CateAdminAdapter.ViewHolder> {
    private ArrayList<danhmucMD> danhmucMDS;
    private Context context;
    ClickListener clickListener;

    public CateAdminAdapter(ArrayList<danhmucMD> danhmucMDS, Context context, ClickListener clickListener) {
        this.danhmucMDS = danhmucMDS;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CateAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemcate_adim, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CateAdminAdapter.ViewHolder holder, final int position) {
        danhmucMD danhmucMD = danhmucMDS.get(position);
        holder.view.setText(danhmucMD.getTenloaisp());
        Picasso.with(context).load(danhmucMD.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.eror)
                .into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(position);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickListener.onLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhmucMDS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView view;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_userad);
            view = itemView.findViewById(R.id.txt_TileCategoryadmin);
            linearLayout = itemView.findViewById(R.id.ln_itemcatead);
        }
    }
}
