package com.example.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shoponline.R;
import com.example.shoponline.model.danhmucMD;
import com.example.shoponline.model.spinerMD;

import java.util.ArrayList;
import java.util.List;

public class SpinerAdapter extends BaseAdapter {
    Context context;
    List<danhmucMD> danhmucMDList;
   int myLayout;



    public SpinerAdapter(Context context, List<danhmucMD> danhmucMDList, int myLayout) {
        this.context = context;
        this.danhmucMDList = danhmucMDList;
        this.myLayout = myLayout;
    }

    @Override
    public int getCount() {
        return danhmucMDList.size();
    }

    @Override
    public Object getItem(int position) {
        return danhmucMDList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout, null);
        TextView names = (TextView) view.findViewById(R.id.txt_name);
//        danhmucMD danhmucMD = danhmucMDList.get(i);
        names.setText(danhmucMDList.get(i).getTenloaisp());
        return view;
    }
}
