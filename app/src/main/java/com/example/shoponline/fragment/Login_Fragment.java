package com.example.shoponline.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoponline.admin.AdminControl;
import com.example.shoponline.CustomToast;
import com.example.shoponline.R;
import com.example.shoponline.Utils;
import com.example.shoponline.activity.MainActivity;
import com.example.shoponline.model.infoUserMD;
import com.example.shoponline.utils.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    ArrayList<infoUserMD> infoUserMDS;
    int id = 0;
    String username = "";
    String phone = "";
    String email = "";
    String location = "";
    String success = "";

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        infoUserMDS = new ArrayList<>();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);


        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {


                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:


                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                Utils.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }


    private void checkValidation() {
        final String getEmailId = emailid.getText().toString();
        final String getPassword = password.getText().toString();

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        } if (getEmailId.equals("Admin")&& getPassword.equals("soanlv")){
            Intent intent = new Intent(getContext(), AdminControl.class);
            startActivity(intent);
            getActivity().finish();
        }else {
            final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onLoginSuccess();
                            progressDialog.dismiss();
                        }

                        private void onLoginSuccess() {
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.LOGIN, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String s = response.trim();
                                    Log.e("res", s);
                                    if (response != null && response.length() > 0) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            for (int i = 0; i <= jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                id = object.getInt("id");
                                                username = object.getString("username");
                                                phone = object.getString("phone");
                                                email = object.getString("email");
                                                location = object.getString("location");
                                                success = object.getString("success");
                                                infoUserMDS.add(new infoUserMD(id, username, phone, email, location, success));
                                                Log.e("size", infoUserMDS.size() + "");
                                                infoUserMD userMD = new infoUserMD();
                                                Log.e("suss", "" + success);
                                                if (success.equals("success")) {

                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putInt("ID", id);
                                                    bundle.putString("Username", username);
                                                    bundle.putString("Phone", phone);
                                                    bundle.putString("Email", email);
                                                    bundle.putString("Location", location);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                } else {

                                                }

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                    }
                                    if (s.equalsIgnoreCase("Error")) {
                                        Toast.makeText(getContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("username", getEmailId);
                                    hashMap.put("password", getPassword);
                                    return hashMap;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                    }, 3000);

        }

    }


}
