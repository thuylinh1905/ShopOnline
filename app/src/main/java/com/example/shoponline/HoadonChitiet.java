package com.example.shoponline;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.activity.MainActivity;
import com.example.shoponline.adapter.GiohangAdapter;
import com.example.shoponline.adapter.HoadonchitietAdapter;
import com.example.shoponline.model.HoadonChitietMD;
import com.example.shoponline.model.SanphamMD;
import com.example.shoponline.utils.CheckConection;
import com.example.shoponline.utils.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HoadonChitiet extends Fragment implements ClickListener {
    int iduser = 0;
    private RecyclerView rv_Hoadonct;
    private TextView txt_khongsp, txt_tonggia;
    ArrayList<HoadonChitietMD> arrayList;
    HoadonchitietAdapter hoadonchitietAdapter;
    int id = 0;
    int masanpham = 0;
    int idkhachhang = 0;
    String tensanpham = "";
    long giasanpham = 0;
    int soluong = 0;
    String hinhanh = "";

    public HoadonChitiet() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoadon_chitiet, container, false);
        rv_Hoadonct = view.findViewById(R.id.rv_hoadon);
        txt_khongsp = view.findViewById(R.id.txt_khongsp);
        arrayList = new ArrayList<>();
        hoadonchitietAdapter = new HoadonchitietAdapter(getContext(), arrayList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_Hoadonct.setLayoutManager(layoutManager);
        rv_Hoadonct.setAdapter(hoadonchitietAdapter);
        Bundle bundle = getArguments();
        iduser = bundle.getInt("idss");
        CheckConection.Show_toast(getContext(), "id : " + iduser);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.GETDONHANG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res", response);
                if (response != null && response.length() > 0) {

                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            id = object.getInt("id");
                            idkhachhang = object.getInt("idkhachhang");
                            masanpham = object.getInt("masanpham");
                            tensanpham = object.getString("tensanpham");
                            giasanpham = object.getInt("giasanpham");
                            soluong = object.getInt("soluong");
                            hinhanh = object.getString("hinhanh");
                            arrayList.add(new HoadonChitietMD(id, masanpham, idkhachhang, tensanpham, giasanpham, soluong, hinhanh));
                            hoadonchitietAdapter.notifyDataSetChanged();
                            if (arrayList.size() <= 0) {
                                hoadonchitietAdapter.notifyDataSetChanged();
                                txt_khongsp.setVisibility(View.VISIBLE);
                                rv_Hoadonct.setVisibility(View.INVISIBLE);
                            } else {
                                hoadonchitietAdapter.notifyDataSetChanged();
                                txt_khongsp.setVisibility(View.INVISIBLE);
                                rv_Hoadonct.setVisibility(View.VISIBLE);
                            }

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
                param.put("idkhachhangs", String.valueOf(iduser));
                return param;
            }
        };
        requestQueue.add(stringRequest);
        return view;
    }

    @Override
    public void onClick(int position) {

        HoadonChitietMD hoadonChitietMD = arrayList.get(position);
        ThongTinHoaDonFragment fragment = new ThongTinHoaDonFragment();
        Bundle bundles = new Bundle();
        bundles.putInt("idsanpham", masanpham);
        bundles.putInt("idkhach", idkhachhang);
        bundles.putString("tensanpham", hoadonChitietMD.getTensanpham());
        bundles.putInt("soluong", hoadonChitietMD.getSoluong());
        bundles.putLong("gia", hoadonChitietMD.getGiasanpham());
        bundles.putString("hinhanh", hoadonChitietMD.getHinhanh());
        fragment.setArguments(bundles);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        getActivity().setTitle("Chi tiết hóa đơn");
        transaction.commit();
    }

    @Override
    public boolean onLongClick(int position) {

        return true;
    }

    @Override
    public void onClick2(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận hủy đơn hàng");
        builder.setMessage("Bạn có chắc muốn hủy ");
        builder.setPositiveButton("Có ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final HoadonChitietMD hoadonChitietMD = arrayList.get(position);
                final int masp = hoadonChitietMD.getMasanpham();

                Log.e("masp",masp+"");
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.HUYDON, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s = response.trim();
                        Log.e("resp",response);
                        if (s.equalsIgnoreCase("ok")){
                            arrayList.remove(position);
                            hoadonchitietAdapter.notifyDataSetChanged();
                            if (arrayList.size()<=0){
                                hoadonchitietAdapter.notifyDataSetChanged();
                                txt_khongsp.setVisibility(View.VISIBLE);
                                rv_Hoadonct.setVisibility(View.INVISIBLE);
                            }else {
                                hoadonchitietAdapter.notifyDataSetChanged();
                                txt_khongsp.setVisibility(View.INVISIBLE);
                                rv_Hoadonct.setVisibility(View.VISIBLE);
                            }

                        }else {

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
                        param.put("masanpham", String.valueOf(masp));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);


            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
hoadonchitietAdapter.notifyDataSetChanged();

            }
        });
        builder.show();

    }
}
