package com.example.shoponline.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.ClickListener;
import com.example.shoponline.EndlessRecyclerViewScrollListener;
import com.example.shoponline.R;
import com.example.shoponline.adapter.itemDetailAdapter;
import com.example.shoponline.admin.adapteradmin.CateAdminAdapter;
import com.example.shoponline.admin.adapteradmin.SanphamAdapterAd;
import com.example.shoponline.model.HoadonChitietMD;
import com.example.shoponline.model.SanphamMD;
import com.example.shoponline.utils.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanphamAdmin extends AppCompatActivity implements ClickListener {
    int ID = 0;
    RecyclerView recyclerView;
    private com.example.shoponline.adapter.itemDetailAdapter itemDetailAdapter;
    ArrayList<SanphamMD> mdArrayList;
    private SanphamMD post;
    SanphamAdapterAd adapter;
    SwipeRefreshLayout limitData;
    int idsp = 0;
    String tensp = "";
    int Giasp = 0;
    String Hinhanhsp = "";
    String motasp = "";
    int IDSP = 0;
    int soluong = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham_admin);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getInt("IID");
        Toast.makeText(this, "id " + ID, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.rv_sphamad);
        mdArrayList = new ArrayList<>();
        adapter = new SanphamAdapterAd(mdArrayList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.GETSPADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res", response);
                if (response != null && response.length() > 0) {

                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            idsp = object.getInt("id");
                            tensp = object.getString("tensanpham");
                            Giasp = object.getInt("giasp");
                            Hinhanhsp = object.getString("hinhanh");
                            motasp = object.getString("motasp");
                            IDSP = object.getInt("idsanpham");
                            soluong = object.getInt("soluong");
                            mdArrayList.add(new SanphamMD(idsp, tensp, Giasp, motasp, Hinhanhsp,  IDSP,soluong));
                            adapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(ID));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(int position) {
        SanphamMD sanphamMD = mdArrayList.get(position);

        Intent intent = new Intent(SanphamAdmin.this, UpdateSanpham.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idass", sanphamMD.getId());
        bundle.putInt("gias", sanphamMD.getGiasp());
        bundle.putString("ten", sanphamMD.getTensanpham());
        bundle.putString("mota", sanphamMD.getMotasanpham());
        bundle.putString("hinhanh", sanphamMD.getHinhanhsp());
        bundle.putInt("soluongs", sanphamMD.getSoluong());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa sp");
        builder.setMessage("Bạn có chắc muốn xóa ");
        builder.setPositiveButton("Có ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final SanphamMD sanphamMD = mdArrayList.get(position);
                final int idsp = sanphamMD.getId();

                Log.e("masp", idsp + "");
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.XOASANPHAM, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s = response.trim();
                        Log.e("resp", response);
                        if (s.equalsIgnoreCase("ok")) {
                            mdArrayList.remove(position);
                            adapter.notifyDataSetChanged();

                        } else {

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("idsp", String.valueOf(idsp));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);


            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.notifyDataSetChanged();

            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onClick2(int position) {

    }
}
