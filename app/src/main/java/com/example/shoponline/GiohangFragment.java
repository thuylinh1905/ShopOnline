package com.example.shoponline;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoponline.activity.MainActivity;
import com.example.shoponline.adapter.GiohangAdapter;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class GiohangFragment extends Fragment implements ClickListener {
    private RecyclerView rv_giohang;
    static TextView txt_thongbao, txt_tongtien;
    Button btnThanhtoan;
    public static GiohangAdapter giohangAdapter;

    int idkhachhang = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giohang, container, false);
        rv_giohang = view.findViewById(R.id.rv_Giohang);
        txt_thongbao = view.findViewById(R.id.txtNosp);
        txt_tongtien = view.findViewById(R.id.txtTongGiatien);
        btnThanhtoan = view.findViewById(R.id.btnThanhtoan);
        giohangAdapter = new GiohangAdapter(getContext(), MainActivity.giohangMDS, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_giohang.setLayoutManager(layoutManager);
        rv_giohang.setAdapter(giohangAdapter);
        CheckDatta();
        EvtntUtis();
        EventButton();
        Bundle bundle=getArguments();
        idkhachhang=bundle.getInt("idss");
        Toast.makeText(getContext(), "id : "+idkhachhang, Toast.LENGTH_SHORT).show();
        return view;
    }

    private void EventButton() {

        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(getContext(), Thongtinkhach.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", idkhachhang);
                intent2.putExtras(bundle);
                startActivity(intent2);
            }
        });
    }


    public static void EvtntUtis() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.giohangMDS.size(); i++) {
            tongtien += MainActivity.giohangMDS.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_tongtien.setText(decimalFormat.format(tongtien) + "VNĐ");
    }

    private void CheckDatta() {
        if (MainActivity.giohangMDS.size() <= 0) {
            giohangAdapter.notifyDataSetChanged();
            txt_thongbao.setVisibility(View.VISIBLE);
            rv_giohang.setVisibility(View.INVISIBLE);
            btnThanhtoan.setVisibility(View.INVISIBLE);
        } else {

            giohangAdapter.notifyDataSetChanged();
            txt_thongbao.setVisibility(View.INVISIBLE);
            rv_giohang.setVisibility(View.VISIBLE);
            btnThanhtoan.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(int position) {

    }

    @Override
    public boolean onLongClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận xóa sản phẩm");
        builder.setMessage("Bạn có chắc muốn xóa ");
        builder.setPositiveButton("Có ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (MainActivity.giohangMDS.size() <= 0) {
                    txt_thongbao.setVisibility(View.VISIBLE);
                    btnThanhtoan.setVisibility(View.INVISIBLE);
                } else {
                    btnThanhtoan.setVisibility(View.VISIBLE);
                    MainActivity.giohangMDS.remove(position);
                    giohangAdapter.notifyDataSetChanged();
                    EvtntUtis();
                    if (MainActivity.giohangMDS.size() <= 0) {
                        txt_thongbao.setVisibility(View.VISIBLE);
                        btnThanhtoan.setVisibility(View.INVISIBLE);
                    } else {
                        btnThanhtoan.setVisibility(View.VISIBLE);
                        txt_thongbao.setVisibility(View.INVISIBLE);
                        giohangAdapter.notifyDataSetChanged();
                        EvtntUtis();
                    }
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                giohangAdapter.notifyDataSetChanged();
                EvtntUtis();
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onClick2(int position) {

    }


}
