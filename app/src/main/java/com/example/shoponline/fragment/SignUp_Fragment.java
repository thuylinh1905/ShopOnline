package com.example.shoponline.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.shoponline.CustomToast;
import com.example.shoponline.Loginmain;
import com.example.shoponline.R;
import com.example.shoponline.Utils;
import com.example.shoponline.utils.Sever;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Fragment extends Fragment implements OnClickListener {
	private static View view;
	private static EditText fullName, emailId, mobileNumber, location,
			password, confirmPassword;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;
	private static FragmentManager fragmentManager;
	public SignUp_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.signup_layout, container, false);
		initViews();
		setListeners();
		return view;
	}

	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		fullName = (EditText) view.findViewById(R.id.fullName);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
		location = (EditText) view.findViewById(R.id.location);
		password = (EditText) view.findViewById(R.id.password);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

		@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			login.setTextColor(csl);
			terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:
			checkValidation();
			break;

		case R.id.already_user:
			new Loginmain().replaceLoginFragment();
			break;
		}

	}

	private void checkValidation() {

		final String getFullName = fullName.getText().toString();
		final String getEmailId = emailId.getText().toString();
		final String getMobileNumber = mobileNumber.getText().toString();
		final String getLocation = location.getText().toString();
		final String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		if (getFullName.equals("") || getFullName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getMobileNumber.equals("") || getMobileNumber.length() == 0
				|| getLocation.equals("") || getLocation.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"All fields are required.");

		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"Your Email Id is Invalid.");

		else if (!getConfirmPassword.equals(getPassword))
			new CustomToast().Show_Toast(getActivity(), view,
					"Both password doesn't match.");

		else if (!terms_conditions.isChecked())
			new CustomToast().Show_Toast(getActivity(), view,
					"Please select Terms and Conditions.");

		else{
			RequestQueue requestQueue= Volley.newRequestQueue(getContext());
			StringRequest stringRequest=new StringRequest(Request.Method.POST, Sever.SIGNUP, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.e("resssss",response);
					String s=response.trim();
					if (s.equalsIgnoreCase("loi")){
						final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
						builder.setTitle("Thông Báo");
						builder.setMessage("Email đã tồn tại ");
						builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						builder.create().show();
					}if (s.equalsIgnoreCase("ok")){
						AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
						builder.setTitle("Thông Báo");
						builder.setMessage("Đăng Kí Thành Công ");
						builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								dialog.cancel();
								fragmentManager
										.beginTransaction()
										.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
										.replace(R.id.frameContainer, new Login_Fragment(),
												Utils.Login_Fragment).commit();
							}
						});
						builder.create().show();
					}else {
						AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
						builder.setTitle("Thông Báo");
						builder.setMessage("Đăng Kí không Thành Công ");
						builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

							dialog.cancel();
							}
						});
						builder.create().show();
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {

				}
			}){
				protected Map<String, String> getParams() throws AuthFailureError {
					HashMap<String,String> hashMap=new HashMap<String, String>();
					hashMap.put("username",getFullName);
					hashMap.put("password",getPassword);
					hashMap.put("phone",getMobileNumber);
					hashMap.put("email",getEmailId);
					hashMap.put("location",getLocation);
					return hashMap;
				}
			};
			requestQueue.add(stringRequest);
		}


	}
}
