package in.co.psd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import in.co.psd.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressbar = new ViewDialog(LoginActivity.this);
        sessionManager = new SessionManager(this);

        binding.textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        binding.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.editMobileNumber.getText().toString().trim().equals("")){

                    Toast.makeText(LoginActivity.this, "Enter Your mobile No", Toast.LENGTH_SHORT).show();

                } else if (binding.editMobileNumber.getText().toString().trim().length() != 10) {

                    Toast.makeText(LoginActivity.this, "Enter Your 10 digit mobile No", Toast.LENGTH_SHORT).show();

                }else if (binding.editPassword.getText().toString().trim().equals("")) {

                    Toast.makeText(LoginActivity.this, "Enter Your Pasword", Toast.LENGTH_SHORT).show();
                }else{

                    userlogin(binding.editMobileNumber.getText().toString().trim(),
                            binding.editPassword.getText().toString().trim());
                }

            }
        });


    }

    public void userlogin(String mobile, String pass){

        progressbar.showDialog();


        JSONObject jsonObject1 = new JSONObject();
        try {

            jsonObject1.put("mobile",mobile);
            jsonObject1.put("pass",pass);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,ApiList.user_login,jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {
                    String status = response.getString("status");

                    if(status.equals("202")){

                        String message = response.getString("message");
                        String user_id = response.getString("user_id");
                        String auth = response.getString("auth");
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                        sessionManager.setISMOBILENO(mobile);
                        sessionManager.setISPASSWORD(pass);
                        sessionManager.setUSERID(user_id);
                        sessionManager.setAUTHKEY(auth);
                        sessionManager.setLogin();

                        startActivity(new Intent(LoginActivity.this,DashBoard.class));

                    }else{

                        String message = response.getString("message");
                        String user_id = response.getString("user_id");
                        String auth = response.getString("auth");
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.hideDialog();
                Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                Log.d("vollyerror",error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sessionManager.isLogin()){

            startActivity(new Intent(LoginActivity.this,DashBoard.class));
        }
    }
}