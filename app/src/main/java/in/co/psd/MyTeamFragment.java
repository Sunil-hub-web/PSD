package in.co.psd;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.psd.databinding.MyteamFragmentBinding;

public class MyTeamFragment extends Fragment {

    MyteamFragmentBinding binding;
    SessionManager sessionManager;
    ViewDialog progressbar;
    ArrayList<LavelModelClass1> lavelModel1 = new ArrayList<>();
    ArrayList<LavelModelClass2> lavelModel2 = new ArrayList<>();
    ArrayList<LavelModelClass3> lavelModel3 = new ArrayList<>();

    int size1 = 0, size2 = 0, size3 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = MyteamFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("My Team");

        binding.textAssets.setBackgroundResource(R.drawable.backgroundcolor);
        binding.textInvite.setBackgroundResource(R.drawable.layoutback);

        sessionManager = new SessionManager(getContext());
        progressbar = new ViewDialog(getActivity());

        binding.linteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.lininviateout.setVisibility(View.GONE);
                binding.linteamout.setVisibility(View.VISIBLE);

                getProfileDetails(sessionManager.getUSERID(), sessionManager.getAUTHKEY());
            }
        });
        binding.lininvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.lininviateout.setVisibility(View.VISIBLE);
                binding.linteamout.setVisibility(View.GONE);

            }
        });
        binding.textInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // binding.textAccount.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textInvite.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textAssets.setBackgroundResource(R.drawable.layoutback);

                binding.textInvite.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textAssets.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

                if(sessionManager.getRefrealCode().equals("")){

                    Toast.makeText(getActivity(), "RefrealCode Not Found", Toast.LENGTH_SHORT).show();

                }else{
                    //String data = "restaurant, 4.2, 20.3426898,  85.81122069999999},https://maps.google.com/?cid=12719621070879037825";

                    String data = sessionManager.getRefrealCode();

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                    sendIntent.setType("text/*");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                }


            }
        });
        binding.textAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textInvite.setBackgroundResource(R.drawable.layoutback);
                binding.textAssets.setBackgroundResource(R.drawable.backgroundcolor);

                binding.textAssets.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textInvite.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            }
        });

        binding.textLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textLevel1.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textLevel2.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel3.setBackgroundResource(R.drawable.layoutback);

                binding.textLevel1.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textLevel2.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel3.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                LavelAdapter1 lavelAdapter = new LavelAdapter1(getActivity(),lavelModel1);
                binding.showRecyclerLavel.setLayoutManager(linearLayoutManager);
                binding.showRecyclerLavel.setHasFixedSize(true);
                binding.showRecyclerLavel.setAdapter(lavelAdapter);
            }
        });

        binding.textLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textLevel1.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel2.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textLevel3.setBackgroundResource(R.drawable.layoutback);

                binding.textLevel1.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel2.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textLevel3.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                LevelAdapter2 lavelAdapter = new LevelAdapter2(getActivity(),lavelModel2);
                binding.showRecyclerLavel.setLayoutManager(linearLayoutManager);
                binding.showRecyclerLavel.setHasFixedSize(true);
                binding.showRecyclerLavel.setAdapter(lavelAdapter);

            }
        });

        binding.textLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textLevel1.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel2.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel3.setBackgroundResource(R.drawable.backgroundcolor);

                binding.textLevel1.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel2.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel3.setTextColor(ContextCompat.getColor(getContext(),R.color.white));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                LevelAdapter3 lavelAdapter = new LevelAdapter3(getActivity(),lavelModel3);
                binding.showRecyclerLavel.setLayoutManager(linearLayoutManager);
                binding.showRecyclerLavel.setHasFixedSize(true);
                binding.showRecyclerLavel.setAdapter(lavelAdapter);

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
                        String level_data = response.getString("level_data");
                        JSONObject jsonObjectlabel = new JSONObject(level_data);

                        JSONArray jsonArray_Level1 = jsonObjectlabel.getJSONArray("Level1");
                        JSONArray jsonArray_Level2 = jsonObjectlabel.getJSONArray("Level2");
                        JSONArray jsonArray_Level3 = jsonObjectlabel.getJSONArray("Level3");

                        if (jsonArray_Level1.length() != 0){

                            for (int i=0; i<jsonArray_Level1.length();i++){

                                JSONObject jsonObject_lavel1 = jsonArray_Level1.getJSONObject(i);

                                String user_name = jsonObject_lavel1.getString("user_name");
                                String user_mobile = jsonObject_lavel1.getString("user_mobile");

                                LavelModelClass1 lavelModelClass1 = new LavelModelClass1(user_name, user_mobile);
                                lavelModel1.add(lavelModelClass1);

                                size1 = lavelModel1.size();

                            }

                        }else{
                            Toast.makeText(getActivity(), "Level 1 Not Found", Toast.LENGTH_SHORT).show();
                        }

                        if (jsonArray_Level2.length() != 0){

                            for (int i=0; i<jsonArray_Level2.length();i++){

                                JSONObject jsonObject_lavel2 = jsonArray_Level2.getJSONObject(i);

                                String user_name = jsonObject_lavel2.getString("user_name");
                                String user_mobile = jsonObject_lavel2.getString("user_mobile");

                                LavelModelClass2 lavelModelClass2 = new LavelModelClass2(user_name, user_mobile);
                                lavelModel2.add(lavelModelClass2);

                                size2 = lavelModel2.size();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Level 2 Not Found", Toast.LENGTH_SHORT).show();
                        }

                        if (jsonArray_Level3.length() != 0){

                            for (int i=0; i<jsonArray_Level3.length();i++){

                                JSONObject jsonObject_lavel3 = jsonArray_Level3.getJSONObject(i);

                                String user_name = jsonObject_lavel3.getString("user_name");
                                String user_mobile = jsonObject_lavel3.getString("user_mobile");

                                LavelModelClass3 lavelModelClass3 = new LavelModelClass3(user_name, user_mobile);
                                lavelModel3.add(lavelModelClass3);

                                size3 = lavelModel3.size();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Level 3 Not Found", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        LavelAdapter1 lavelAdapter = new LavelAdapter1(getActivity(),lavelModel1);
                        binding.showRecyclerLavel.setLayoutManager(linearLayoutManager);
                        binding.showRecyclerLavel.setHasFixedSize(true);
                        binding.showRecyclerLavel.setAdapter(lavelAdapter);

                        binding.textLevel1.setBackgroundResource(R.drawable.backgroundcolor);
                        binding.textLevel2.setBackgroundResource(R.drawable.layoutback);
                        binding.textLevel3.setBackgroundResource(R.drawable.layoutback);

                        binding.textLevel1.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                        binding.textLevel2.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                        binding.textLevel3.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

                        int totalmember = size1 + size2 + size3;

                        binding.showmember1.setText(String.valueOf("( "+totalmember+" )"));
                        binding.showmember2.setText(String.valueOf(String.valueOf("( "+totalmember+" )")));

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
}
