package com.example.shoponline.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.R;
import com.example.shoponline.utils.Sever;

import java.util.HashMap;
import java.util.Map;

public class Themdanhmuc extends AppCompatActivity {
    private Button btn_Xacnhan, btn_huy;
    private EditText edt_tendanhmuc, edt_hinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themdanhmuc);
        btn_Xacnhan = findViewById(R.id.btn_xacnhans);
        btn_huy = findViewById(R.id.btn_huys);
        edt_tendanhmuc = findViewById(R.id.edt_Tendanhmuc);
        edt_hinhanh = findViewById(R.id.edt_hinhanh);
        btn_Xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tendm = edt_tendanhmuc.getText().toString().trim();
                final String hinanh = edt_hinhanh.getText().toString().trim();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.Themdanhmuc, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ress", response);
                        String s = response.trim();
                        if (s.equalsIgnoreCase("ok")) {
                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            AdminControl.getData(getApplicationContext());
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("tenloaisp", tendm);
                        hashMap.put("hinh", hinanh);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}
