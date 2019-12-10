package com.example.shoponline;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.shoponline.adapter.TimkiemAdapter;
import com.example.shoponline.adapter.itemDetailAdapter;
import com.example.shoponline.fragment.ChitietlDanhmucFragment;
import com.example.shoponline.fragment.chitietHang;
import com.example.shoponline.model.SanphamMD;
import com.example.shoponline.model.TimkiemMD;
import com.example.shoponline.utils.Sever;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Timkiem extends Fragment implements ClickListener {

    AutoCompleteTextView editText;
    ArrayList<SanphamMD> arrayList;
    ArrayList<String> strings;
    RecyclerView recyclerView;
    Button button;
    TimkiemAdapter adapter;
    int idsp = 0;
    String tensp = "";
    Integer Giasp = 0;
    String Hinhanhsp = "";
    String motasp = "";
    int soluong=0;
    int IDSP = 0;
    int idusser=0;
    private SanphamMD post;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timkiem, container, false);
        editText = view.findViewById(R.id.edt_tim);
        Bundle bundle=getArguments();
        idusser=bundle.getInt("iduser");
        Toast.makeText(getContext(), "" + idusser, Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.rv_tim);
        arrayList = new ArrayList<>();

        button = view.findViewById(R.id.btnTim);
        adapter = new TimkiemAdapter(getContext(), arrayList, this);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        strings = new ArrayList<>();

        for (int i = 0; i < ChitietlDanhmucFragment.mdArrayList.size(); i++) {
            strings.add(ChitietlDanhmucFragment.mdArrayList.get(i).getTensanpham());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, strings);
            editText.setAdapter(adapter);
            Log.e("si", strings.toString());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editText.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.TIMKIEM, new Response.Listener<String>() {
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
                                    arrayList.add(new SanphamMD(idsp, tensp, Giasp, motasp, Hinhanhsp, IDSP, soluong));
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
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("tensp", name);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        return view;
    }

    @Override
    public void onClick(int position) {
        post = new SanphamMD();
        post = arrayList.get(position);
        int ID = post.getId();
        int Giachitiet = post.getGiasp();
        String Tenchitiet = post.getTensanpham();
        String HinhChiTiet = post.getHinhanhsp();
        String Mota = post.getMotasanpham();
        int idsanoham = post.getIDsanpham();
        chitietHang fragment = new chitietHang();
        Bundle bundle = new Bundle();
        bundle.putInt("iduss", idusser);
        bundle.putInt("ID", ID);
        bundle.putInt("Gia", Giachitiet);
        bundle.putString("name", Tenchitiet);
        bundle.putString("Hinhanh", HinhChiTiet);
        bundle.putString("Mota", Mota);
        bundle.putInt("Idsanpham", idsanoham);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        getActivity().setTitle("Chi tiết sản phẩm");
        transaction.commit();
    }

    @Override
    public boolean onLongClick(int position) {
        return false;
    }

    @Override
    public void onClick2(int position) {

    }
}
