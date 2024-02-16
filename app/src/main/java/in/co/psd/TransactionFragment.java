package in.co.psd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import in.co.psd.databinding.ProductFragmentBinding;
import in.co.psd.databinding.TransactionFragmentBinding;

public class TransactionFragment extends Fragment {

    TransactionFragmentBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;

    ArrayList<TransactionModel> transaction_all = new ArrayList<>();
    ArrayList<TransactionModel> transaction_withdraw = new ArrayList<>();
    ArrayList<TransactionModel> transaction_recherage = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = TransactionFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("Transaction");

        progressbar = new ViewDialog(getActivity());
        sessionManager = new SessionManager(getActivity());

        binding.textAll.setBackgroundResource(R.drawable.otptextview_otp);
        binding.textRecharge.setBackgroundResource(R.drawable.layoutback);
        binding.textWithdraw.setBackgroundResource(R.drawable.layoutback);

        getTrnsaction(sessionManager.getUSERID());

        binding.textAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textAll.setBackgroundResource(R.drawable.otptextview_otp);
                binding.textRecharge.setBackgroundResource(R.drawable.layoutback);
                binding.textWithdraw.setBackgroundResource(R.drawable.layoutback);

                binding.textAll.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                binding.textRecharge.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
                binding.textWithdraw.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                TransactionAdapter transactionAdapter = new TransactionAdapter(getActivity(),transaction_all);
                binding.transactionRecycler.setHasFixedSize(true);
                binding.transactionRecycler.setLayoutManager(linearLayoutManager);
                binding.transactionRecycler.setAdapter(transactionAdapter);

            }
        });

        binding.textWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textAll.setBackgroundResource(R.drawable.layoutback);
                binding.textRecharge.setBackgroundResource(R.drawable.layoutback);
                binding.textWithdraw.setBackgroundResource(R.drawable.otptextview_otp);

                binding.textWithdraw.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                binding.textRecharge.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
                binding.textAll.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                TransactionAdapter transactionAdapter = new TransactionAdapter(getActivity(),transaction_withdraw);
                binding.transactionRecycler.setHasFixedSize(true);
                binding.transactionRecycler.setLayoutManager(linearLayoutManager);
                binding.transactionRecycler.setAdapter(transactionAdapter);

            }
        });

        binding.textRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textAll.setBackgroundResource(R.drawable.layoutback);
                binding.textRecharge.setBackgroundResource(R.drawable.otptextview_otp);
                binding.textWithdraw.setBackgroundResource(R.drawable.layoutback);

                binding.textRecharge.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                binding.textWithdraw.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
                binding.textAll.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                TransactionAdapter transactionAdapter = new TransactionAdapter(getActivity(),transaction_recherage);
                binding.transactionRecycler.setHasFixedSize(true);
                binding.transactionRecycler.setLayoutManager(linearLayoutManager);
                binding.transactionRecycler.setAdapter(transactionAdapter);

            }
        });

        return binding.getRoot();
    }

    public void getTrnsaction(String userId){

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("userId",userId);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.transaction, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {
                    String status = response.getString("status");
                    String message = response.getString("message");
                    String user_details = response.getString("user_details");

                    transaction_all.clear();
                    transaction_withdraw.clear();
                    transaction_recherage.clear();

                    if (status.equals("202")){

                        JSONArray jsonArray_trans = new JSONArray(user_details);

                        if (jsonArray_trans.length() != 0){

                            for (int i=0;i<jsonArray_trans.length();i++){

                                JSONObject jsonObject_trans = jsonArray_trans.getJSONObject(i);

                                String transaction_id = jsonObject_trans.getString("transaction_id");
                                String transaction_user = jsonObject_trans.getString("transaction_user");
                                String transaction_amount = jsonObject_trans.getString("transaction_amount");
                                String transaction_payOn = jsonObject_trans.getString("transaction_payOn");
                                String transaction_payId = jsonObject_trans.getString("transaction_payId");
                                String transaction_img = jsonObject_trans.getString("transaction_img");
                                String transaction_stat = jsonObject_trans.getString("transaction_stat");
                                String transaction_date = jsonObject_trans.getString("transaction_date");
                                String transaction_type = jsonObject_trans.getString("transaction_type");
                                String transaction_purpose = jsonObject_trans.getString("transaction_purpose");

                                TransactionModel transactionModel = new TransactionModel(
                                        transaction_id, transaction_user, transaction_amount, transaction_payOn, transaction_payId,
                                        transaction_img, transaction_stat, transaction_date, transaction_type , transaction_purpose
                                );

                                transaction_all.add(transactionModel);

                                if (transaction_purpose.equals("Recherge")){

                                    TransactionModel transactionModel1 = new TransactionModel(
                                            transaction_id, transaction_user, transaction_amount, transaction_payOn, transaction_payId,
                                            transaction_img, transaction_stat, transaction_date, transaction_type , transaction_purpose
                                    );

                                    transaction_recherage.add(transactionModel1);

                                } else if (transaction_purpose.equals("withdrwal")) {

                                    TransactionModel transactionModel1 = new TransactionModel(
                                            transaction_id, transaction_user, transaction_amount, transaction_payOn, transaction_payId,
                                            transaction_img, transaction_stat, transaction_date, transaction_type , transaction_purpose
                                    );

                                    transaction_withdraw.add(transactionModel1);
                                }
                            }
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        TransactionAdapter transactionAdapter = new TransactionAdapter(getActivity(),transaction_all);
                        binding.transactionRecycler.setHasFixedSize(true);
                        binding.transactionRecycler.setLayoutManager(linearLayoutManager);
                        binding.transactionRecycler.setAdapter(transactionAdapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }

}
