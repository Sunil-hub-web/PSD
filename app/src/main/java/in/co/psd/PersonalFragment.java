package in.co.psd;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import in.co.psd.databinding.PersonalFragmentBinding;
import in.co.psd.databinding.RedeemFragmintBinding;

public class PersonalFragment extends Fragment {

    PersonalFragmentBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = PersonalFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("Personal");

        progressbar = new ViewDialog(getActivity());
        sessionManager = new SessionManager(getActivity());

        getProfileDetails(sessionManager.getUSERID(), sessionManager.getAUTHKEY());

        //binding.textTotalrecharge.setText("");

        Log.d("userdetails",sessionManager.getUSERID()+"  "+sessionManager.getAUTHKEY());

        binding.relRecharage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new RechargeFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.relWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new WithdrawFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.linMyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new MyProductFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.linBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new BankAccountFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.linChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ChangePasswordFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.linRedeemGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new RedeemGiftFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.linTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new TransactionFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.linWithDrawalPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new WidthdrawalPassFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", "SchoolUniform");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout_Condition();
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
                        String user_auth = jsonObject_user_details.getString("user_auth");
                        String user_refCode = jsonObject_user_details.getString("user_refCode");
                        String user_stat = jsonObject_user_details.getString("user_stat");
                        String user_wallet = jsonObject_user_details.getString("user_wallet");

                        sessionManager.setUSERID(user_id);
                        sessionManager.setAUTHKEY(user_auth);
                        sessionManager.setRefrealCode(user_id);
                        sessionManager.setLogin();

                        binding.userName.setText(user_name);
                        binding.userMobileNo.setText(user_mobile);

                        binding.textTotalrecharge.setText(user_wallet);

                        if (sessionManager.getBALANCEAMOUNT().equals("defvalue")){

                            binding.textTotalbalance.setText("0");

                        }else{

                            binding.textTotalbalance.setText(sessionManager.getBALANCEAMOUNT());
                        }


                        Log.d("userreferalcode",user_id+"   "+sessionManager.getRefrealCode());

                        getHomeDetails(userId);

                    }else{

                        String message = response.getString("message");
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

    public void getHomeDetails(String userId){

        progressbar.showDialog();


        Map<String,String> params = new HashMap<>();
        params.put("userId",userId);

        JSONObject jsonObject1 = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.userHome, jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {
                    String status = response.getString("status");

                    if(status.equals("202")){

                        String message = response.getString("message");
                        String user_details = response.getString("user_details");
                        JSONObject jsonObject_user_details = new JSONObject(user_details);
                        String withdrawal = jsonObject_user_details.getString("withdrawal");
                        String recharge = jsonObject_user_details.getString("recharge");
                        String income = jsonObject_user_details.getString("income");
                        String product = jsonObject_user_details.getString("product");
                        String balance = jsonObject_user_details.getString("balance");

                        binding.textTotalbalance.setText(balance);
                        binding.textTotalrecharge.setText(recharge);
                        binding.textTotalIncome.setText(income);
                        binding.textTotalAssets.setText(product);
                        binding.textTextWithDraw.setText(withdrawal);

                    }else{

                        String message = response.getString("message");
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

    public void logout_Condition() {

        //Show Your Another AlertDialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.condition_logout);
        dialog.setCancelable(false);
        Button btn_No = dialog.findViewById(R.id.no);
        TextView textView = dialog.findViewById(R.id.editText);
        Button btn_Yes = dialog.findViewById(R.id.yes);

        btn_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

                dialog.dismiss();
                getActivity().finish();
                //System.exit(1);

            }
        });
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.drawable.homecard_back1);

    }
}
