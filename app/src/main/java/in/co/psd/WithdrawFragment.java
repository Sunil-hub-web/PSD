package in.co.psd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.co.psd.databinding.ProductFragmentBinding;
import in.co.psd.databinding.WithdrawFragmentBinding;

public class WithdrawFragment extends Fragment {

    WithdrawFragmentBinding binding;
    SessionManager sessionManager;
    String str_userwallet,str_password;
    ViewDialog progressbar;
    double duser_wallet = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = WithdrawFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("Withdraw");

        sessionManager = new SessionManager(getActivity());
        //str_mobileNo = sessionManager.getISMOBILENO();
        //str_password = sessionManager.getISPASSWORD();

        progressbar = new ViewDialog(getActivity());

        getProfileDetails(sessionManager.getUSERID(), sessionManager.getAUTHKEY());

        binding.btnWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.texttotalbalance.getText().toString().trim().equals("0")){

                    Toast.makeText(getActivity(), "Your Wallet Balance is Zero", Toast.LENGTH_SHORT).show();

                } else if (binding.editWithdrawAmount.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Enter Your Withdraw Amount", Toast.LENGTH_SHORT).show();

                } else if (binding.editWithdrawPassword.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Enter Your Withdraw Password", Toast.LENGTH_SHORT).show();

                }else{

                    int withdrawamount = Integer.valueOf(binding.editWithdrawAmount.getText().toString().trim());
                    int withdrawwalletamount = Integer.valueOf(str_userwallet);

                    if(withdrawamount > withdrawwalletamount){

                        Toast.makeText(getActivity(), "Not Sufficient Wallet Amount", Toast.LENGTH_SHORT).show();

                    }else{

                        if (withdrawamount > 150){

                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                            userwithdrawamount(sessionManager.getUSERID(),
                                    binding.editWithdrawAmount.getText().toString().trim(), date);

                        }else{

                            Toast.makeText(getActivity(), "Withdraw Amount must be above 150", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        return binding.getRoot();
    }

    public void getProfileDetails(String userId,String auth){

        progressbar.showDialog();


        Map<String,String> params = new HashMap<>();
        params.put("userId",userId);
        params.put("auth",auth);

        JSONObject jsonObject1 = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.user_details, jsonObject1, new Response.Listener<JSONObject>() {
            @SuppressLint("GetInstance")
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {
                    String status = response.getString("status");
                    if(status.equals("202")){

                        String message = response.getString("message");
                        String user_details = response.getString("user_details");
                        JSONObject jsonObject_user_details = new JSONObject(user_details);
                        String user_id = jsonObject_user_details.getString("user_id");
                        String user_name = jsonObject_user_details.getString("user_name");
                        String user_mobile = jsonObject_user_details.getString("user_mobile");
                        String user_password = jsonObject_user_details.getString("user_password");
                        String user_withdrawPass = jsonObject_user_details.getString("user_withdrawPass");
                        String user_auth = jsonObject_user_details.getString("user_auth");
                        String user_refCode = jsonObject_user_details.getString("user_refCode");
                        String user_stat = jsonObject_user_details.getString("user_stat");
                        str_userwallet = jsonObject_user_details.getString("user_wallet");

                        sessionManager.setBALANCEAMOUNT(str_userwallet);

                        sessionManager.setUSERID(user_id);
                        sessionManager.setAUTHKEY(user_auth);
                        sessionManager.setRefrealCode(user_id);
                        sessionManager.setLogin();

                        duser_wallet = Double.valueOf(str_userwallet);

                        binding.texttotalbalance.setText("RS  "+sessionManager.getBALANCEAMOUNT());


                    }else{

                        String message = response.getString("message");
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbar.hideDialog();
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void userwithdrawamount(String uid, String amt, String date){

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("uid",uid);
            jsonObject.put("amt",amt);
            jsonObject.put("date",date);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.withdrwal, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("201")){

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();



                        double balanceamount = Double.valueOf(str_userwallet) - Double.valueOf(amt);
                        String str_balanceamount = String.valueOf(balanceamount);
                        sessionManager.setBALANCEAMOUNT(str_balanceamount);

                        binding.texttotalbalance.setText("RS  "+sessionManager.getBALANCEAMOUNT());

                    }else{

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
                Log.d("userdetails",error.toString());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }
}
