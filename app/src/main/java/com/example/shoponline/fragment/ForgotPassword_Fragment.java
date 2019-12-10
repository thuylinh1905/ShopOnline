package com.example.shoponline.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.example.shoponline.Doimk;
import com.example.shoponline.Loginmain;
import com.example.shoponline.R;
import com.example.shoponline.Utils;
import com.example.shoponline.utils.Sever;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword_Fragment extends Fragment implements
        OnClickListener {
	private static View view;

	private static EditText emailId;
	private static TextView submit, back;
	private static FragmentManager fragmentManager;
	public ForgotPassword_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forgotpassword_layout, container,
				false);
		initViews();
		setListeners();
		return view;
	}

	// Initialize the views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		emailId = (EditText) view.findViewById(R.id.registered_emailid);
		submit = (TextView) view.findViewById(R.id.forgot_button);
		back = (TextView) view.findViewById(R.id.backToLoginBtn);

		// Setting text selector over textviews
		@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			back.setTextColor(csl);
			submit.setTextColor(csl);

		} catch (Exception e) {
		}

	}

	// Set Listeners over buttons
	private void setListeners() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backToLoginBtn:

			// Replace Login Fragment on Back Presses
			new Loginmain().replaceLoginFragment();
			break;

		case R.id.forgot_button:

			// Call Submit button task
			submitButtonTask();
			break;

		}

	}

	private void submitButtonTask() {
		final String getEmailId = emailId.getText().toString();

		// Pattern for email id validation
		Pattern p = Pattern.compile(Utils.regEx);

		// Match the pattern
		Matcher m = p.matcher(getEmailId);

		// First check if email id is not null else show error toast
		if (getEmailId.equals("") || getEmailId.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"Please enter your Email Id.");

		// Check if email id is valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"Your Email Id is Invalid.");

		// Else submit email id and fetch passwod or do your stuff
		else{
			RequestQueue requestQueue= Volley.newRequestQueue(getContext());
			StringRequest stringRequest=new StringRequest(Request.Method.POST, Sever.QUENMK, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.e("resquen",response);
					String s=response.trim();
					if (s.equalsIgnoreCase("ok")){
						Doimk doimk=new Doimk();
						Bundle bundle=new Bundle();
						bundle.putString("email",getEmailId);
						doimk.setArguments(bundle);
						fragmentManager
								.beginTransaction()
								.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
								.replace(R.id.frameContainer,
										doimk,
										Utils.ForgotPassword_Fragment).commit();
					}else {
						new CustomToast().Show_Toast(getActivity(), view,
								"Email không tồn tại ");
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {

				}
			}){

				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					HashMap<String,String>hashMap=new HashMap<>();
					hashMap.put("email",getEmailId);
					return hashMap;
				}
			};
			requestQueue.add(stringRequest);
		}

	}
}