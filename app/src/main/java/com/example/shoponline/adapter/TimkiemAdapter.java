package com.example.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.ClickListener;
import com.example.shoponline.R;
import com.example.shoponline.model.SanphamMD;
import com.example.shoponline.model.TimkiemMD;
import com.example.shoponline.model.danhmucMD;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class TimkiemAdapter extends RecyclerView.Adapter<TimkiemAdapter.ViewHlder> {
    Context context;
    List<SanphamMD> sanphamMDS;
    ClickListener clickListener;

    public TimkiemAdapter(Context context, List<SanphamMD> sanphamMDS, ClickListener clickListener) {
        this.context = context;
        this.sanphamMDS = sanphamMDS;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TimkiemAdapter.ViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tim, parent, false);
        return new ViewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimkiemAdapter.ViewHlder holder, final int position) {
        SanphamMD sanphamMD = sanphamMDS.get(position);
        holder.TenSp.setText(sanphamMD.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        holder.Giasp.setText("Giá : " + decimalFormat.format(sanphamMD.getGiasp()) + "VND");

        if (sanphamMD.getSoluong()<=0){
            holder.soluong.setText("hết hàng");
        }else {
            holder.soluong.setText("Còn hàng");
        }
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
    }


    @Override
    public int getItemCount() {
        return sanphamMDS.size();
    }


    public class ViewHlder extends RecyclerView.ViewHolder {
        ImageView imgHinhsp;
        TextView TenSp, Giasp, soluong;
        CardView cardView;

        public ViewHlder(@NonNull View itemView) {
            super(itemView);
            imgHinhsp = itemView.findViewById(R.id.imgSanphamtim);
            TenSp = itemView.findViewById(R.id.txtTensptim);
            Giasp = itemView.findViewById(R.id.txtGiassptim);
            cardView = itemView.findViewById(R.id.Cv_spnewtim);
            soluong = itemView.findViewById(R.id.txt_viewslnewtim);
        }
    }
}
