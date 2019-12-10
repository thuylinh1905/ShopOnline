package com.example.shoponline.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shoponline.GiohangFragment;
import com.example.shoponline.R;
import com.example.shoponline.activity.MainActivity;
import com.example.shoponline.model.GiohangMD;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;


public class chitietHang extends Fragment {
    private TextView txtTensp, txtPcire, txtMota;
    Spinner spinnerSP;
    Button btnDatmua;
    private ImageView imageView;
    int id = 0;
    int Giachitiet = 0;
    String Tenchitiet = "";
    String HinhChiTiet = "";
    String Mota = "";
    int idsanoham = 0;
int idkhachhang=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_hang, container, false);
        txtTensp = view.findViewById(R.id.namesp);
        txtPcire = view.findViewById(R.id.pircesp);
        txtMota = view.findViewById(R.id.txtMotachitiet);
        spinnerSP = view.findViewById(R.id.spsp);
        btnDatmua = view.findViewById(R.id.addGio);
        imageView = view.findViewById(R.id.imgchitietsp);
        getInfor();
        EventSpiner();
        EventButon();
        return view;
    }

    private void EventButon() {
        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.giohangMDS.size() > 0) {
                    int sl = Integer.parseInt(spinnerSP.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.giohangMDS.size(); i++) {
                        if (MainActivity.giohangMDS.get(i).getIdsp() == id) {
                            MainActivity.giohangMDS.get(i).setSoluong(MainActivity.giohangMDS.get(i).getSoluong() + sl);
                            if (MainActivity.giohangMDS.get(i).getSoluong() >= 10) {
                                MainActivity.giohangMDS.get(i).setSoluong(10);
                            }
                            MainActivity.giohangMDS.get(i).setGiasp(Giachitiet * MainActivity.giohangMDS.get(i).getSoluong());
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int soluong = Integer.parseInt(spinnerSP.getSelectedItem().toString());
                        long Giamoi = soluong * Giachitiet;
                        MainActivity.giohangMDS.add(new GiohangMD(id, Tenchitiet, Giamoi, HinhChiTiet, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinnerSP.getSelectedItem().toString());
                    long Giamoi = soluong * Giachitiet;
                    MainActivity.giohangMDS.add(new GiohangMD(id, Tenchitiet, Giamoi, HinhChiTiet, soluong));
                }
                FragmentManager fragmentManager = getFragmentManager();
                GiohangFragment giohangFragment=new GiohangFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("idss",idkhachhang);
                giohangFragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container,giohangFragment);
                getActivity().setTitle("Giỏ Hàng");
                transaction.commit();
            }
        });
    }

    private void EventSpiner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getContext(), R.layout.support_simple_spinner_dropdown_item, soluong);
        spinnerSP.setAdapter(arrayAdapter);
    }

    private void getInfor() {
        Bundle bundle = getArguments();
        id = bundle.getInt("ID");
        Giachitiet = bundle.getInt("Gia");
        idkhachhang=bundle.getInt("iduss");
        Tenchitiet = bundle.getString("name");
        HinhChiTiet = bundle.getString("Hinhanh");
        Giachitiet = bundle.getInt("Gia");
        Mota = bundle.getString("Mota");
        idsanoham = bundle.getInt("Idsanpham");
        txtTensp.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPcire.setText("Giá : " + decimalFormat.format(Giachitiet) + "VND");
        txtMota.setText(Mota);
        txtTensp.setText(Tenchitiet);
        Picasso.with(getContext()).load(HinhChiTiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.eror)
                .into(imageView);
        Toast.makeText(getContext(), "id"+idkhachhang, Toast.LENGTH_SHORT).show();
    }


}
