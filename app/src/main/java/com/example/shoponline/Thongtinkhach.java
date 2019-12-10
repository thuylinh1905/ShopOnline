package com.example.shoponline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.shoponline.activity.MainActivity;
import com.example.shoponline.utils.CheckConection;
import com.example.shoponline.utils.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhach extends AppCompatActivity {
    int idkhach = 0;
    private EditText edt_tenKhachhang, edt_Sodienthoai, edt_email, edt_diachi;
    private Button btn_xacNhan, btn_Huy;
    int masp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhach);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idkhach = bundle.getInt("ID");
        Toast.makeText(this, " id " + idkhach, Toast.LENGTH_SHORT).show();
        edt_tenKhachhang = findViewById(R.id.edt_namekhachhang);
        edt_diachi = findViewById(R.id.edt_diachi);
        edt_email = findViewById(R.id.edt_email);
        edt_Sodienthoai = findViewById(R.id.edt_phonekk);
        btn_xacNhan = findViewById(R.id.btn_xacnhan);
        btn_Huy = findViewById(R.id.btn_huy);
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConection.haveNetworkConnection(this)) {
            EvenButon();
        } else {
            CheckConection.Show_toast(this, "kiểm tra internet");
        }
    }

    private void EvenButon() {
        btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = edt_tenKhachhang.getText().toString().trim();
                final String sdt = edt_Sodienthoai.getText().toString().trim();
                final String email = edt_email.getText().toString().trim();
                final String diachi = edt_diachi.getText().toString().trim();
                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0 && diachi.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.DONHANG, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            Log.e("madonhang", response);
                            if (response.length() > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request1 = new StringRequest(Request.Method.POST, Sever.CHITIETDONHANG, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response2) {
                                        String s = response2.trim();
                                        if (s.equalsIgnoreCase("1")) {
                                            CheckConection.Show_toast(getApplicationContext(), "thành công");
                                            MainActivity.giohangMDS.clear();
                                           finish();
                                           GiohangFragment.giohangAdapter.notifyDataSetChanged();
                                            GiohangFragment.EvtntUtis();


                                        } else {
                                            CheckConection.Show_toast(getApplicationContext(), " Lỗi");
                                        }
                                        //

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {

                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.giohangMDS.size(); i++) {
                                            JSONObject object = new JSONObject();
                                            try {
                                                object.put("idkhachhang", idkhach);
                                                object.put("masanpham", MainActivity.giohangMDS.get(i).idsp);
                                                object.put("tensanpham", MainActivity.giohangMDS.get(i).getTensp());
                                                object.put("giasanpham", MainActivity.giohangMDS.get(i).getGiasp());
                                                object.put("soluong", MainActivity.giohangMDS.get(i).getSoluong());
                                                object.put("hinhanh", MainActivity.giohangMDS.get(i).getHinhanh());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(object);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }

                                };
                                queue.add(request1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {

                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", ten);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("email", email);
                            hashMap.put("diachi", diachi);
                            hashMap.put("idkhachhang", String.valueOf(idkhach));
                            for (int j = 0; j < MainActivity.giohangMDS.size(); j++) {
                                masp = MainActivity.giohangMDS.get(j).getIdsp();
                                hashMap.put("idsanpham", String.valueOf(masp));
                            }
                            return hashMap;
                        }


                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConection.Show_toast(getApplicationContext(), "Không được bỏ trống các trường thuông tin");
                }
            }
        });
    }
}
