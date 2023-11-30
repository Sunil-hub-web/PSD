package in.co.psd;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.co.psd.databinding.ViewproductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModelClass> productModelClasses1;
    ViewproductBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;
    Activity activity;

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

                productByNow(sessionManager.getUSERID(),product.getProduct_id(),product.product_amt,date);

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
}
