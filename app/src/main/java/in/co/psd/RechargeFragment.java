package in.co.psd;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.co.psd.databinding.ProductFragmentBinding;
import in.co.psd.databinding.RechargeFragmentBinding;

public class RechargeFragment extends Fragment {

    RechargeFragmentBinding binding;
    ViewDialog progressbar;
    SessionManager sessionManager;
    String date,time,userId,imageDetails = "",paymentChooseOption = "";
    int year, month, day, hour, minute;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    Bitmap selectedImage,rotated;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RechargeFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("Recharge");

        progressbar = new ViewDialog(getActivity());
        sessionManager = new SessionManager(getContext());
        userId = sessionManager.getUSERID();

        binding.textGPay.setBackgroundResource(R.drawable.otptextview_otp);
        binding.textPhonePay.setBackgroundResource(R.drawable.layoutback);
        binding.textPaytm.setBackgroundResource(R.drawable.layoutback);
        binding.textUPID.setBackgroundResource(R.drawable.layoutback);

        binding.textGPay.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        binding.textPhonePay.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        binding.textPaytm.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        binding.textUPID.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

        binding.qrcodeImage.setVisibility(View.VISIBLE);
        binding.qrcodeImage.setImageResource(R.drawable.gpayqrcode);
        binding.editUPID.setVisibility(View.GONE);

        paymentChooseOption = binding.textGPay.getText().toString().trim();

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                //Uri imageUri = data.getData();
                try {
                    // Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    //click_image.setImageBitmap(bitmap);

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);

                    rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);

                    // click_image.animate().rotation(180).start();

                    selectedImage = bitmap;

                    binding.editShowImagePtah.setText(bitmap.toString());

                    imageDetails = getEncodedString(selectedImage);

                    Log.d("imageselect","selectimg  "+selectedImage);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });

        binding.textGPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textGPay.setBackgroundResource(R.drawable.otptextview_otp);
                binding.textPhonePay.setBackgroundResource(R.drawable.layoutback);
                binding.textPaytm.setBackgroundResource(R.drawable.layoutback);
                binding.textUPID.setBackgroundResource(R.drawable.layoutback);

                binding.textGPay.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textPhonePay.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textPaytm.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textUPID.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

                binding.qrcodeImage.setVisibility(View.VISIBLE);
                binding.qrcodeImage.setImageResource(R.drawable.gpayqrcode);
                binding.editUPID.setVisibility(View.GONE);

                paymentChooseOption = binding.textGPay.getText().toString().trim();


            }
        });

        binding.textPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textGPay.setBackgroundResource(R.drawable.layoutback);
                binding.textPhonePay.setBackgroundResource(R.drawable.layoutback);
                binding.textPaytm.setBackgroundResource(R.drawable.otptextview_otp);
                binding.textUPID.setBackgroundResource(R.drawable.layoutback);

                binding.textGPay.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textPhonePay.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textPaytm.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textUPID.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

                binding.qrcodeImage.setVisibility(View.VISIBLE);
                binding.qrcodeImage.setImageResource(R.drawable.paytemqrcode);
                binding.editUPID.setVisibility(View.GONE);

                paymentChooseOption = binding.textPaytm.getText().toString().trim();

            }
        });

        binding.textUPID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textGPay.setBackgroundResource(R.drawable.layoutback);
                binding.textPhonePay.setBackgroundResource(R.drawable.layoutback);
                binding.textPaytm.setBackgroundResource(R.drawable.layoutback);
                binding.textUPID.setBackgroundResource(R.drawable.otptextview_otp);

                binding.textGPay.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textPhonePay.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textPaytm.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textUPID.setTextColor(ContextCompat.getColor(getContext(),R.color.white));

                binding.qrcodeImage.setVisibility(View.GONE);
                binding.qrcodeImage.setImageResource(R.drawable.phonepayqrcode);
                binding.editUPID.setVisibility(View.VISIBLE);

                paymentChooseOption = binding.textUPID.getText().toString().trim();
            }
        });

        binding.textPhonePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textGPay.setBackgroundResource(R.drawable.layoutback);
                binding.textPhonePay.setBackgroundResource(R.drawable.otptextview_otp);
                binding.textPaytm.setBackgroundResource(R.drawable.layoutback);
                binding.textUPID.setBackgroundResource(R.drawable.layoutback);

                binding.textGPay.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textPhonePay.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textPaytm.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textUPID.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

                binding.qrcodeImage.setVisibility(View.VISIBLE);
                binding.qrcodeImage.setImageResource(R.drawable.phonepayqrcode);
                binding.editUPID.setVisibility(View.GONE);

                paymentChooseOption = binding.textPhonePay.getText().toString().trim();

            }
        });

        binding.editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
                time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                        month = month+1;

                        String fmonth=""+month;
                        String fDate=""+dayOfMonth;

                        if(month<10){
                            fmonth ="0"+month;
                        }
                        if (dayOfMonth<10){
                            fDate="0"+dayOfMonth;
                        }

                        String date = fDate+"/"+fmonth+"/"+year;
                        //String date = year+"-"+month+"-"+day;
                        binding.editDate.setText(date);
                        //getReport("10","01/04/2023");

                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA)

                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    } else {

                        showSettingsAlert();
                    }
                } else {

                    chooseImage(getActivity());
                }

//                if (checkAndRequestPermissions(getActivity())) {
//                    chooseImage(getActivity());
//                }
            }
        });

        binding.btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.editAmount.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Enter Your Amount", Toast.LENGTH_SHORT).show();

                }else if (binding.editDate.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Select Your Date", Toast.LENGTH_SHORT).show();

                }else if (binding.editTransactionId.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Enter Your Transaction", Toast.LENGTH_SHORT).show();

                }else if (imageDetails.equals("")){

                    Toast.makeText(getActivity(), "Select Your Image", Toast.LENGTH_SHORT).show();

                }else if (paymentChooseOption.equals("")){

                    Toast.makeText(getActivity(), "Select Your Payment Option", Toast.LENGTH_SHORT).show();

                }else{

                    yourRecharges(userId,binding.editAmount.getText().toString().trim(),
                            paymentChooseOption,binding.editTransactionId.getText().toString().trim(),
                            binding.editDate.getText().toString().trim(),imageDetails);
                }
            }
        });


        return binding.getRoot();
    }

    public void yourRecharges(String uid, String amt, String payon, String paymentId, String date, String file){
        progressbar.showDialog();

        JSONObject jsonObject1 = new JSONObject();
        try {

            jsonObject1.put("uid",uid);
            jsonObject1.put("amt",amt);
            jsonObject1.put("payon",payon);
            jsonObject1.put("paymentId",paymentId);
            jsonObject1.put("date",date);
            jsonObject1.put("file",file);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.recharge, jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {
                    String status = response.getString("status");
                    String message = response.getString("message");
                    if (status.equals("201")){

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }

        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    private void chooseImage(Context context) {

        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (optionsMenu[i].equals("Take Photo")) {

                    // Open the camera and get the photo

                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (optionsMenu[i].equals("Choose from Gallery")) {

                    // Launch the photo picker and let the user choose only images.
                    pickMedia.launch(new PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                            .build());

                    //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly));

                    // choose from  external storage

//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto, 1);

                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }
    // function to check permission
    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext().getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    chooseImage(getContext());
                }
                break;
        }
    }
    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }
    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //startInstalledAppDetailsActivity(MainActivity.this);
                    }
                });

        alertDialog.show();
    }

    private String getEncodedString(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }
}
