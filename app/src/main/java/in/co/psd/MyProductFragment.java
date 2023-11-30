package in.co.psd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.co.psd.databinding.MyproductFragmentBinding;
import in.co.psd.databinding.ProductFragmentBinding;

public class MyProductFragment extends Fragment {

    MyproductFragmentBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;
    ArrayList<ProductModelClass> productModelClasses = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = MyproductFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("My Product");

        progressbar = new ViewDialog(getActivity());
        sessionManager = new SessionManager(getActivity());

        try {
            productDetails(sessionManager.getUSERID());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return binding.getRoot();
    }

    public void productDetails(String uid) throws JSONException {

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("uid",uid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.my_products, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {
                    //JSONObject jsonObject = new JSONObject(response);

                    String status = response.getString("status");
                    String message = response.getString("message");
                    String poducts_details = response.getString("result");

                    if (status.equals("202")){

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray_poducts_details = new JSONArray(poducts_details);

                        for (int i=0;i<jsonArray_poducts_details.length();i++){

                            JSONObject jsonObject_product = jsonArray_poducts_details.getJSONObject(i);

                            String product_id = jsonObject_product.getString("product_id");
                            String product_title = jsonObject_product.getString("product_title");
                            String product_desc = jsonObject_product.getString("product_desc");
                            String product_amt = jsonObject_product.getString("product_amt");
                            String product_lockDays = jsonObject_product.getString("product_lockDays");
                            String product_purchaseLimit = jsonObject_product.getString("product_purchaseLimit");
                            String product_owner = jsonObject_product.getString("product_owner");
                            String product_banner = jsonObject_product.getString("product_banner");
                            String product_catelog = jsonObject_product.getString("product_catelog");
                            String product_stat = jsonObject_product.getString("product_stat");

                            ProductModelClass productModelClass = new ProductModelClass(
                                    product_id, product_title, product_desc, product_amt, product_lockDays, product_purchaseLimit, product_owner,
                                    product_banner, product_catelog, product_stat
                            );

                            productModelClasses.add(productModelClass);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        ProductAdapter productAdapter = new ProductAdapter(getActivity(),productModelClasses);
                        binding.myProductRecycler.setLayoutManager(linearLayoutManager);
                        binding.myProductRecycler.setHasFixedSize(true);
                        binding.myProductRecycler.setAdapter(productAdapter);

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
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }
}
