package com.example.shoponline.admin.adapteradmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.ClickListener;
import com.example.shoponline.R;
import com.example.shoponline.adapter.itemDetailAdapter;
import com.example.shoponline.model.SanphamMD;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapterAd extends RecyclerView.Adapter<SanphamAdapterAd.ViewHolder> {
    ArrayList<SanphamMD> mangsp;
    Context context;
    ClickListener clickListener;

    public SanphamAdapterAd(ArrayList<SanphamMD> mangsp, Context context, ClickListener clickListener) {
        this.mangsp = mangsp;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SanphamAdapterAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemsp_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanphamAdapterAd.ViewHolder holder, final int position) {
        SanphamMD sanphamMD = mangsp.get(position);
        holder.TenSp.setText(sanphamMD.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        holder.Giasp.setText("Giá : " + decimalFormat.format(sanphamMD.getGiasp()) + "VND");
        Picasso.with(context).load(sanphamMD.getHinhanhsp())
                .error(R.drawable.eror)
                .placeholder(R.drawable.noimage)
                .into(holder.imgHinhsp);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(position);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickListener.onLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangsp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhsp;
        TextView TenSp, Giasp;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhsp = itemView.findViewById(R.id.imgSanphamssad);
            TenSp = itemView.findViewById(R.id.txtTenspssad);
            Giasp = itemView.findViewById(R.id.txtGiasspssad);
            cardView = itemView.findViewById(R.id.Cv_spnewsad);
        }
    }
}
