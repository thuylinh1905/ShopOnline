package com.example.shoponline;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.utils.Sever;

import java.util.HashMap;
import java.util.Map;


public class Doimk extends Fragment implements View.OnClickListener {

    private static View view;

    private static EditText newpassword, Cofpassword;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    String email = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doimk, container, false);
        newpassword = (EditText) view.findViewById(R.id.edt_Newpassword);
        Cofpassword = (EditText) view.findViewById(R.id.edt_Cofpassword);
        loginButton = (Button) view.findViewById(R.id.submitBtn);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_passwords);
        setListeners();
        Bundle bundle = getArguments();
        email = bundle.getString("email");
        Toast.makeText(getContext(), "" + email, Toast.LENGTH_SHORT).show();
        return view;
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {


                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);

                            newpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            newpassword.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                            Cofpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            Cofpassword.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                        } else {
                            show_hide_password.setText(R.string.show_pwd);

                            newpassword.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            newpassword.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                            Cofpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Cofpassword.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());

                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitBtn:
                putData();
                break;


        }
    }

    private void putData() {
        final String newpass = newpassword.getText().toString();
        String Cof = Cofpassword.getText().toString();
        if (newpass.equals("") || newpass.length() == 0 && Cof.equals("") || Cof.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your NewPass and Pass.");
        } else if (!Cof.equals(newpass)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Nhập đúng Cofimpassword");
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.DOIMK, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String s = response.trim();
                    if (s.equalsIgnoreCase("ok")){
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Đổi mật khẩu thành công");
                    }else {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Không thành công");
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
                    hashMap.put("email", email);
                    hashMap.put("password", newpass);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
