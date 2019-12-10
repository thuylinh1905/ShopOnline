package com.example.shoponline;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.model.SanphamMD;
import com.example.shoponline.utils.Sever;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinHoaDonFragment extends Fragment {
    int idsanpham = 0;
    int idkhachhang = 0;
    String tensanpham = "";
    long giasanpham = 0;
    int soluong = 0;
    String hinhanh = "";
    int id = 0;
    String Tenkhachhang = "";
    String Diachi = "";
    String SDT = "";
    String Email = "";
    TextView txt_tensp, txtGiasp, txtTenkk, txtEmailkk, txtDiachi, txtSđt, txtsoluong;
    ImageView imageView;

    public ThongTinHoaDonFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_hoa_don, container, false);
        txt_tensp = view.findViewById(R.id.namesphd);
        txtDiachi = view.findViewById(R.id.diachikk);
        txtEmailkk = view.findViewById(R.id.emailkk);
        txtSđt = view.findViewById(R.id.SĐTkk);
        txtGiasp = view.findViewById(R.id.pircesphd);
        txtTenkk = view.findViewById(R.id.namekk);
        txtsoluong = view.findViewById(R.id.soluong);
        imageView = view.findViewById(R.id.imgchitiethd);
        Bundle bundle = getArguments();
        idsanpham = bundle.getInt("idsanpham");
        idkhachhang = bundle.getInt("idkhach");
        tensanpham = bundle.getString("tensanpham");
        giasanpham = bundle.getLong("gia");
        soluong = bundle.getInt("soluong");
        hinhanh = bundle.getString("hinhanh");
        Toast.makeText(getContext(), "idsp : " + idsanpham + "idKhach : " + idkhachhang, Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.GETHOADONCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() > 0) {

                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            id = object.getInt("id");
                            Tenkhachhang = object.getString("tenkhachhang");
                            SDT = object.getString("sodienthoai");
                            Email = object.getString("email");
                            Diachi = object.getString("diachi");

                        }
                        txt_tensp.setText("Tên Sp : " + tensanpham);
                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                        txtGiasp.setText("Giá SP : " + decimalFormat.format(giasanpham) + "VNĐ");
                        txtsoluong.setText("Số Lượng : " + soluong);
                        txtTenkk.setText("Tên Khách hàng : " + Tenkhachhang);
                        txtDiachi.setText("Địa Chỉ Khách hàng : " + Diachi);
                        txtEmailkk.setText("Địa chỉ mail " + Email);
                        txtSđt.setText("SĐT : " + SDT);
                        Picasso.with(getContext()).load(hinhanh).into(imageView);
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
                param.put("idkhachhang", String.valueOf(idkhachhang));
                return param;
            }

        };
        requestQueue.add(stringRequest);

        return view;
    }

}
