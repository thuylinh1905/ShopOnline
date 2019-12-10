package com.example.shoponline.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AdminupdateCate extends AppCompatActivity {
    int idcate = 0;
    String tendm = "";
    String Hinhanh = "";
    private EditText edt_Tensp, edt_hinhanh;
    private Button btn_xacnhan, btn_huy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminupdate_cate);
        edt_Tensp = findViewById(R.id.edt_Tendanhmucup);
        edt_hinhanh = findViewById(R.id.edt_hinhanhup);
        btn_xacnhan = findViewById(R.id.btn_xacnhansup);

        btn_huy = findViewById(R.id.btn_huysup);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idcate = bundle.getInt("Iddct");
        tendm = bundle.getString("Tendm");
        Hinhanh = bundle.getString("Hinhanh");

        Toast.makeText(this, "" + idcate, Toast.LENGTH_SHORT).show();
        edt_Tensp.setText(tendm);
        edt_hinhanh.setText(Hinhanh);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edt_Tensp.getText().toString().trim();
                final String hinhanh = edt_hinhanh.getText().toString().trim();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.UPDATEDM, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ok", response);
                        String s=response.trim();
                        if (s.equalsIgnoreCase("ok")){

                            finish();
                            Toast.makeText(AdminupdateCate.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
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
                        hashMap.put("idsp", String.valueOf(idcate));
                        hashMap.put("tenloaisp", name);
                        hashMap.put("hinh", hinhanh);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}
