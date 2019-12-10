package com.example.shoponline.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.R;
import com.example.shoponline.adapter.SpinerAdapter;
import com.example.shoponline.model.danhmucMD;
import com.example.shoponline.utils.Sever;

import java.util.HashMap;
import java.util.Map;

public class UpdateSanpham extends AppCompatActivity {
    EditText edt_tensp, edt_giasp, edt_motasp, edt_hinhanh, edt_Soluongsp;
    Spinner sp_loaisp;
    Button btn_xacnhan, btn_huy;

    String tensp = "";
    int Giasp = 0;
    String Hinhanhsp = "";
    String motasp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sanpham);
        edt_tensp = findViewById(R.id.edt_Tenspadup);
        edt_giasp = findViewById(R.id.edt_Giaspadup);
        edt_hinhanh = findViewById(R.id.edt_Hinhanhupad);
        edt_motasp = findViewById(R.id.edt_motaspADup);
        sp_loaisp = findViewById(R.id.spLoaispadup);
        btn_xacnhan = findViewById(R.id.btn_xacnhanspup);
        edt_Soluongsp = findViewById(R.id.edt_Soluongspadup);
        btn_huy = findViewById(R.id.btn_huyspup);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        edt_tensp.setText(bundle.getString("ten"));
        edt_motasp.setText(bundle.getString("mota"));
        edt_giasp.setText(bundle.getString("gias"));
        edt_hinhanh.setText(bundle.getString("hinhanh"));
        edt_Soluongsp.setText(bundle.getString("soluongs"));

        SpinerAdapter spinerAdapter = new SpinerAdapter(this, AdminControl.arrDanhmuc, R.layout.item_spiner_loai_chi);
        sp_loaisp.setAdapter(spinerAdapter);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int idsp = bundle.getInt("idass");
                Toast.makeText(getApplicationContext(), "" +idsp, Toast.LENGTH_SHORT).show();
                final String tensps = edt_tensp.getText().toString();
                final String giasp = edt_giasp.getText().toString();
                final String hinhanh = edt_hinhanh.getText().toString();
                final String mota = edt_motasp.getText().toString();
                final int soluong = Integer.parseInt(edt_Soluongsp.getText().toString());
                danhmucMD danhmucMD = (danhmucMD) sp_loaisp.getSelectedItem();
                final int idLoaisp = danhmucMD.getId();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.UPDATESP, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res", response);
                        String s=response.trim();
                        if (response.equalsIgnoreCase("ok")){
                            finish();
                            Toast.makeText(UpdateSanpham.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
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
                        hashMap.put("idsp", String.valueOf(idsp));
                        hashMap.put("tensanpham", tensps);
                        hashMap.put("giasp", giasp);
                        hashMap.put("hinhanh", hinhanh);
                        hashMap.put("motasp", mota);
                        hashMap.put("idsanpham", String.valueOf(idLoaisp));
                        hashMap.put("soluong", String.valueOf(soluong));


                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}
