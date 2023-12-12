package in.co.psd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import in.co.psd.databinding.ViewproductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModelClass> productModelClasses1;
    ViewproductBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;
    Activity activity;
    static Cipher cipher;

    public ProductAdapter(FragmentActivity activity, ArrayList<ProductModelClass> productModelClasses) {

        this.context = activity;
        this.activity = activity;
        this.productModelClasses1 = productModelClasses;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ViewproductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        progressbar = new ViewDialog(activity);
        sessionManager = new SessionManager(context);

        ProductModelClass product = productModelClasses1.get(position);

        Double d_daliyincome = Double.valueOf(product.getProduct_owner());
        Double d_validateincome = Double.valueOf(product.getProduct_lockDays());
        Double total_revinue = d_daliyincome * d_validateincome;
        String str_totalrevinue = String.valueOf(total_revinue);

        holder.viewproductBinding.textPrice1.setText(product.getProduct_amt());
        holder.viewproductBinding.textDailyIncome1.setText("Rs "+product.getProduct_owner());
        holder.viewproductBinding.textValidityPeriod1.setText(product.getProduct_lockDays()+" / Days");
        holder.viewproductBinding.textPurchaseLimit1.setText(product.getProduct_purchaseLimit()+" / Person");
        holder.viewproductBinding.textTotalRevenue1.setText("Rs "+str_totalrevinue);

        String image = "https://projects.conjuror.in/investor/"+product.getProduct_banner();
        Picasso.with(context).load(image).into(holder.viewproductBinding.productImage);

        binding.btnBYNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                getProfileDetails(sessionManager.getUSERID(), sessionManager.getAUTHKEY(),product.getProduct_id(),
                        product.product_amt,date);

                //productByNow(sessionManager.getUSERID(),product.getProduct_id(),product.product_amt,date);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productModelClasses1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewproductBinding viewproductBinding;
        public ViewHolder(ViewproductBinding itemView) {
            super(itemView.getRoot());
            this.viewproductBinding = itemView;
        }
    }

    public void productByNow(String uid, String productID, String amt, String date){

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("uid",uid);
            jsonObject.put("productID",productID);
            jsonObject.put("amt",amt);
            jsonObject.put("date",date);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.add_product_booking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {
                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("201")){

                        Toast.makeText(context, "Your Product Add Successfully", Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbar.hideDialog();
                Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }

    public void getProfileDetails(String userId,String auth,String productID, String amt, String date){

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
                        String user_wallet = jsonObject_user_details.getString("user_wallet");


                        sessionManager.setUSERID(user_id);
                        sessionManager.setAUTHKEY(user_auth);
                        sessionManager.setRefrealCode(user_id);
                        sessionManager.setLogin();



                        double duser_wallet = Double.valueOf(user_wallet);
                        double damt = Double.valueOf(amt);

                        if (duser_wallet > damt ){

                            productByNow(userId, productID, amt, date);

                        }else{

                            Toast.makeText(context, "Please Recharges And Buy", Toast.LENGTH_SHORT).show();
                        }


                    }else{

                        String message = response.getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbar.hideDialog();
                Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
}
