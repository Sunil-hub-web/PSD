package in.co.psd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import in.co.psd.databinding.ActivityLoginBinding;
import in.co.psd.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressbar = new ViewDialog(RegisterActivity.this);
        sessionManager = new SessionManager(RegisterActivity.this);

        binding.textSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.editUserFullName.getText().toString().trim().equals("")){

                    Toast.makeText(RegisterActivity.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }else if (binding.editMobileNumber.getText().toString().trim().equals("")){

                    Toast.makeText(RegisterActivity.this, "Enter Your Mobile NO", Toast.LENGTH_SHORT).show();
                }else if (binding.editMobileNumber.getText().toString().trim().length() != 10){

                    Toast.makeText(RegisterActivity.this, "Enter Your 10 digit Mobile NO", Toast.LENGTH_SHORT).show();
                }/*else if (binding.editEmailId.getText().toString().trim().equals("")){

                    Toast.makeText(RegisterActivity.this, "Enter Your E_Mail Id ", Toast.LENGTH_SHORT).show();
                }*/else if (binding.editPassword.getText().toString().trim().equals("")){

                    Toast.makeText(RegisterActivity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                }else if (binding.editPasswordAgain.getText().toString().trim().equals("")){

                    Toast.makeText(RegisterActivity.this, "Enter Your Confirm Password", Toast.LENGTH_SHORT).show();

                } else if (!binding.editWidthdrawalPassword.getText().toString().trim().equals(
                        binding.editConfirmWidthdrawalPassword.getText().toString().trim())){

                    Toast.makeText(RegisterActivity.this, "Widthdrawal Password Don't Match", Toast.LENGTH_SHORT).show();

                } else {

                    if (binding.editPassword.getText().toString().trim().equals(binding.editPasswordAgain.getText().toString().trim())){

                        userRegister(binding.editUserFullName.getText().toString().trim(),
                                binding.editMobileNumber.getText().toString().trim(),
                                binding.editPassword.getText().toString().trim(),
                                binding.editInvitationCode.getText().toString().trim(),
                                binding.editWidthdrawalPassword.getText().toString().trim());

                    }else{

                        Toast.makeText(RegisterActivity.this, "Password Don't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public  void userRegister(String name, String mobile, String pass, String refCode,String withdrawPass){

        progressbar.showDialog();

        JSONObject jsonObject1 = new JSONObject();
        try {

            jsonObject1.put("name",name);
            jsonObject1.put("mobile",mobile);
            jsonObject1.put("pass",pass);
            jsonObject1.put("withdrawPass",withdrawPass);
            jsonObject1.put("refCode",refCode);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.user_register, jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");


                    if (status.equals("201")){

                        String message = response.getString("message");
                        String user_id = response.getString("user_id");
                        String auth = response.getString("auth");

                        sessionManager.setWITHDRAWPASSWORD(withdrawPass);

                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));


                    }else{
                        String message = response.getString("message");
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.hideDialog();
                Toast.makeText(RegisterActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
}