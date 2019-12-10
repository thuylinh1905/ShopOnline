package com.example.shoponline.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.R;
import com.example.shoponline.model.danhmucMD;
import com.example.shoponline.utils.CheckConection;
import com.example.shoponline.utils.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminControl extends AppCompatActivity implements View.OnClickListener {
    private Button btn_quanlydanhmuc, btn_quanlysanpham, btn_themdanhmuc, btn_themsp;
    public static ArrayList<danhmucMD> arrDanhmuc;
   public static int id = 0;
   public static String tenloaisp = "";
   public static String hinhanh = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
        btn_quanlydanhmuc = findViewById(R.id.btn_quanlydanhmuc);
        btn_quanlysanpham = findViewById(R.id.btn_quanlysp);
        btn_themdanhmuc = findViewById(R.id.btn_adddanhmuc);
        btn_themsp = findViewById(R.id.btn_addsanpham);
        btn_quanlysanpham.setOnClickListener(this);
        btn_themsp.setOnClickListener(this);
        btn_themdanhmuc.setOnClickListener(this);
        btn_quanlydanhmuc.setOnClickListener(this);
        arrDanhmuc = new ArrayList<>();
        getData(this);
    }

    public static void getData(final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.Show_toast(context, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_quanlydanhmuc:

                Intent intent =new Intent(AdminControl.this,CategoryAdmin.class);
                startActivity(intent);
                break;
            case R.id.btn_quanlysp:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_adddanhmuc:
                Intent intent2 =new Intent(AdminControl.this, Themdanhmuc.class);
                startActivity(intent2);
                break;
            case R.id.btn_addsanpham:
                Intent intent3 =new Intent(AdminControl.this, Themsanpham.class);
                startActivity(intent3);
                break;

        }
    }
}
