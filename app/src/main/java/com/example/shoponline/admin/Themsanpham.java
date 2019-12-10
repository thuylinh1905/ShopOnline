package com.example.shoponline.admin;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.shoponline.model.spinerMD;
import com.example.shoponline.utils.Sever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Themsanpham extends AppCompatActivity {
   private EditText edt_tensp, edt_giasp, edt_motasp, edt_hinhanh,edt_Soluongsp;
   private Spinner sp_loaisp;
   private Button btn_xacnhan, btn_huy;

    List<spinerMD> mdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsanpham);
        edt_tensp = findViewById(R.id.edt_Tenspad);
        edt_giasp = findViewById(R.id.edt_Giaspad);
        edt_hinhanh = findViewById(R.id.edt_Hinhanh);
        edt_motasp = findViewById(R.id.edt_motaspAD);
        sp_loaisp = findViewById(R.id.spLoaisp);
        btn_xacnhan = findViewById(R.id.btn_xacnhansp);
        edt_Soluongsp = findViewById(R.id.edt_Soluongsp);
        btn_huy = findViewById(R.id.btn_huysp);

        mdList = new ArrayList<>();
        SpinerAdapter spinerAdapter = new SpinerAdapter(this, AdminControl.arrDanhmuc, R.layout.item_spiner_loai_chi);
        sp_loaisp.setAdapter(spinerAdapter);

        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String tensps = edt_tensp.getText().toString();
                final String giasp = edt_giasp.getText().toString();
                final String hinhanh = edt_hinhanh.getText().toString();
                final String mota = edt_motasp.getText().toString();
                final int soluong = Integer.parseInt(edt_Soluongsp.getText().toString());
                danhmucMD danhmucMD = (danhmucMD) sp_loaisp.getSelectedItem();
                final int idLoaisp = danhmucMD.getId();
                Toast.makeText(Themsanpham.this, "" + idLoaisp, Toast.LENGTH_SHORT).show();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.Themsanpham, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ress",response);
                        String s=response.trim();
                        if (s.equalsIgnoreCase("ok")){
                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
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

                        hashMap.put("giasps", giasp);
                        hashMap.put("hinhanhs", hinhanh);
                        hashMap.put("motasps", mota);
                        hashMap.put("idsanphams", String.valueOf(idLoaisp));
                        hashMap.put("soluongs", String.valueOf(soluong));
                        hashMap.put("tensp",tensps);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}
