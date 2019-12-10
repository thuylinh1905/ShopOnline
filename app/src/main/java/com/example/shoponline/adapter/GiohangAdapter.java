package com.example.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.ClickListener;
import com.example.shoponline.GiohangFragment;
import com.example.shoponline.R;
import com.example.shoponline.activity.MainActivity;
import com.example.shoponline.model.GiohangMD;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.ViewHlder> {
    Context context;
    ArrayList<GiohangMD> giohangMDS;
    ClickListener clickListener;

    public GiohangAdapter(Context context, ArrayList<GiohangMD> giohangMDS, ClickListener clickListener) {
        this.context = context;
        this.giohangMDS = giohangMDS;
        this.clickListener = clickListener;
    }

    public GiohangAdapter() {
    }

    @NonNull
    @Override
    public GiohangAdapter.ViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new ViewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GiohangAdapter.ViewHlder holder, final int position) {
        GiohangMD giohangMD = giohangMDS.get(position);
        holder.Tengiohang.setText(giohangMD.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.Giagiohang.setText(decimalFormat.format(giohangMD.getGiasp()) + "VND");
        Picasso.with(context).load(giohangMD.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgGiohang);
        holder.btnValus.setText(giohangMD.getSoluong() + "");
        int sl = Integer.parseInt(holder.btnValus.getText().toString());
        if (sl >= 10) {
            holder.btnPlus.setVisibility(View.INVISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            holder.btnMinus.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            holder.btnPlus.setVisibility(View.VISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
        }
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slnew = Integer.parseInt(holder.btnValus.getText().toString()) +1;

                int slht = MainActivity.giohangMDS.get(position).getSoluong();
                long giaht = MainActivity.giohangMDS.get(position).getGiasp();
                MainActivity.giohangMDS.get(position).setSoluong(slnew);
                long gianew = (giaht * slnew) / slht;
                MainActivity.giohangMDS.get(position).setGiasp(gianew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.Giagiohang.setText(decimalFormat.format(gianew) + "VND");
                GiohangFragment.EvtntUtis();
                if (slnew > 9) {
                    holder.btnPlus.setVisibility(View.INVISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValus.setText(String.valueOf(slnew));
                } else {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValus.setText(String.valueOf(slnew));
                }
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slnew = Integer.parseInt(holder.btnValus.getText().toString()) - 1;
                int slht = MainActivity.giohangMDS.get(position).getSoluong();
                long giaht = MainActivity.giohangMDS.get(position).getGiasp();
                MainActivity.giohangMDS.get(position).setSoluong(slnew);
                long gianew = (giaht * slnew) / slht;
                MainActivity.giohangMDS.get(position).setGiasp(gianew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.Giagiohang.setText(decimalFormat.format(gianew) + "VND");
                GiohangFragment.EvtntUtis();
                if (slnew < 2) {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.INVISIBLE);
                    holder.btnValus.setText(String.valueOf(slnew));
                } else {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValus.setText(String.valueOf(slnew));
                }
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
        return giohangMDS.size();
    }

    public class ViewHlder extends RecyclerView.ViewHolder {
        TextView Tengiohang, Giagiohang;
        ImageView imgGiohang;
        Button btnMinus, btnPlus, btnValus;
        LinearLayout linearLayout;

        public ViewHlder(@NonNull View itemView) {
            super(itemView);
            Tengiohang = itemView.findViewById(R.id.txt_Tengiohang);
            Giagiohang = itemView.findViewById(R.id.txt_Giagiohang);
            imgGiohang = itemView.findViewById(R.id.imganhgiohang);
            btnMinus = itemView.findViewById(R.id.btnminus);
            btnPlus = itemView.findViewById(R.id.btnplus);
            btnValus = itemView.findViewById(R.id.btnvalus);
            linearLayout = itemView.findViewById(R.id.ln_giohang);
        }
    }
}
