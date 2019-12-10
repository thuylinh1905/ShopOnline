package com.example.shoponline.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.ClickListener;
import com.example.shoponline.R;
import com.example.shoponline.adapter.categoryAdapter;
import com.example.shoponline.model.danhmucMD;
import com.example.shoponline.utils.CheckConection;
import com.example.shoponline.utils.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class categoryFragment extends Fragment implements ClickListener  {

    private ArrayList<danhmucMD> arrDanhmuc;
    RecyclerView recyclerView;
    private categoryAdapter adapter;
    private danhmucMD  post;
    int id = 0;
    String tenloaisp = "";
    String hinhanh = "";
    int iduser = 0;
    String username = "";
    String phone = "";
    String email = "";
    String location = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.rv_category);

        if (CheckConection.haveNetworkConnection(getContext())) {

        } else {
            CheckConection.Show_toast(getContext(), "kiểm tra mạng internet");
            DialogMessegerInternet();
        }
        arrDanhmuc = new ArrayList<>();
        adapter = new categoryAdapter(arrDanhmuc, getActivity(),this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        getDuLieu();
        Bundle bundle=getArguments();
        iduser=bundle.getInt("id");
        username=bundle.getString("name");
        phone=bundle.getString("phone");
        email=bundle.getString("email");
        location=bundle.getString("loation");
        Toast.makeText(getContext(), "id : " + iduser + "\n" + "name" + username + "\n" + "phone" + phone + "\n" + "email" + email + "\n" + "vị trí " + location, Toast.LENGTH_LONG).show();
        return view;
    }

    private void getDuLieu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                CheckConection.Show_toast(getContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void DialogMessegerInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông Báo");
        builder.setMessage("Vui Lòng Kiểm Tra Kết Nối Internet!");
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(int position) {
        post = new danhmucMD();
        post = arrDanhmuc.get(position);
        String namecate=post.getTenloaisp();
        int ID=post.getId();
        ChitietlDanhmucFragment fragment = new ChitietlDanhmucFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", ID);
        bundle.putInt("IDUs", iduser);
        bundle.putString("names",username);
        bundle.putString("phones",phone);
        bundle.putString("emails",email);
        bundle.putString("locations",location);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        getActivity().setTitle(namecate);
        transaction.commit();


        Toast.makeText(getContext(), "danhmuc"+ post.getTenloaisp(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(int position) {
        return false;
    }

    @Override
    public void onClick2(int position) {

    }


}
