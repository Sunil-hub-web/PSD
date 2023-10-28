package in.co.psd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
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

import java.util.HashMap;
import java.util.Map;

import in.co.psd.databinding.BankaccountFragmentBinding;
import in.co.psd.databinding.ChangepasswordFragmentBinding;
import in.co.psd.databinding.ProductFragmentBinding;

public class ChangePasswordFragment extends Fragment {

    ChangepasswordFragmentBinding binding;
    SessionManager sessionManager;
    String str_mobileNo,str_password;
    ViewDialog progressbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = ChangepasswordFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("Change Password");

        sessionManager = new SessionManager(getActivity());
        str_mobileNo = sessionManager.getISMOBILENO();
        str_password = sessionManager.getISPASSWORD();

        progressbar = new ViewDialog(getActivity());
        
        binding.btnChangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (binding.editOLDPassword.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Enter Old Password", Toast.LENGTH_SHORT).show();
                    
                }else if (binding.editNewPassword.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Enter New Password", Toast.LENGTH_SHORT).show();
                    
                }else if (binding.editNewPasswordAgain.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Enter Confrm Password", Toast.LENGTH_SHORT).show();

                }else{
                    
                    if (binding.editNewPassword.getText().toString().trim().equals(binding.editNewPasswordAgain.getText().toString().trim())){

                        changePassword(sessionManager.getISMOBILENO(),
                                binding.editOLDPassword.getText().toString().trim(),
                                binding.editNewPassword.getText().toString().trim());
                    }else{

                        Toast.makeText(getActivity(), "Password Mismatched", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    public void changePassword(String mobile, String oldPass, String newPass){

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("mobile",mobile);
            jsonObject.put("oldPass",oldPass);
            jsonObject.put("newPass",newPass);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.change_password, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");

                    if (status.equals("200")){

                        String message = response.getString("message");
                        String user_id = response.getString("user_id");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        
                    }else{
                        
                        String message = response.getString("message");
                        String user_id = response.getString("user_id");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbar.hideDialog();
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }
}