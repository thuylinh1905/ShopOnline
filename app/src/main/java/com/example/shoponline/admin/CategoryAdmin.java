package com.example.shoponline.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.ClickListener;
import com.example.shoponline.R;
import com.example.shoponline.admin.adapteradmin.CateAdminAdapter;
import com.example.shoponline.model.danhmucMD;
import com.example.shoponline.utils.CheckConection;
import com.example.shoponline.utils.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryAdmin extends AppCompatActivity implements ClickListener {
    private ArrayList<danhmucMD> arrDanhmuc;
    RecyclerView recyclerView;
    public static CateAdminAdapter adapter;
    int id = 0;
    String tenloaisp = "";
    String hinhanh = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin);
        recyclerView = findViewById(R.id.rv_categoryad);

        arrDanhmuc = new ArrayList<>();
        adapter = new CateAdminAdapter(arrDanhmuc, this, this);
        adapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.GETLOAISP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            id = object.getInt("id");
                            tenloaisp = object.getString("tenloaisp");
                            hinhanh = object.getString("hinh");
                            arrDanhmuc.add(new danhmucMD(id, tenloaisp, hinhanh));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.Show_toast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(int position) {
        danhmucMD danhmucMD = arrDanhmuc.get(position);
        int Id = danhmucMD.getId();
        Intent intent = new Intent(CategoryAdmin.this, SanphamAdmin.class);
        Bundle bundle = new Bundle();
        bundle.putInt("IID", Id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn chỉ mục");
        builder.setMessage("Bạn muốn sửa hay xóa.Lưu Ý nếu xóa thì các sản phẩm thuộc thư mục cũng sẽ mất theo bạn nên can nhắc  ");
        builder.setPositiveButton("Xóa ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final danhmucMD danhmucMDs = arrDanhmuc.get(position);
                final int iddm = danhmucMDs.getId();

                Log.e("masp", iddm + "");
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.XOADANHMUC, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s = response.trim();
                        Log.e("resp", response);
                        if (s.equalsIgnoreCase("ok")) {
                            arrDanhmuc.remove(position);
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
                        param.put("iddm", String.valueOf(iddm));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });


        builder.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                danhmucMD danhmucMD = arrDanhmuc.get(position);

                Intent intent = new Intent(getApplicationContext(), AdminupdateCate.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Iddct", danhmucMD.getId());
                bundle.putString("Tendm", danhmucMD.getTenloaisp());
                bundle.putString("Hinhanh", danhmucMD.getHinhanhsp());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onClick2(int position) {

    }
}
