package in.co.psd;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.psd.databinding.BankaccountFragmentBinding;
import in.co.psd.databinding.FragmentEditbankaccountBinding;

public class BankAccountFragment extends Fragment {

    FragmentEditbankaccountBinding binding;
    BankaccountFragmentBinding binding_bank;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    Bitmap selectedImage,rotated;
    String imageDetails = "",userID;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    ViewDialog progressbar;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentEditbankaccountBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("Bank Account");

        progressbar = new ViewDialog(getActivity());
        sessionManager = new SessionManager(getActivity());
        userID = sessionManager.getUSERID();
        viewBankAccoutDetails(userID);

        Log.d("useriddetails",userID);

        binding.btnAddBankAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addBankAccountDetails();
            }
        });
        binding.btnEditupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.btnEditupload.getText().toString().trim().equals("Edit")){

                    binding.btnEditupload.setText("Upload");
                    binding.linUploadbankdetails.setVisibility(View.VISIBLE);

                }else{

                    binding.btnEditupload.setText("Edit");
                    binding.linUploadbankdetails.setVisibility(View.GONE);

                    if(binding.userFullName.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your Name", Toast.LENGTH_SHORT).show();

                    }else if(binding.bankName.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your Bank Name", Toast.LENGTH_SHORT).show();

                    }else if(binding.bankAccountNo.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your Bank AccountNo", Toast.LENGTH_SHORT).show();

                    }else if(binding.iFCSCode.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your IFCSCode", Toast.LENGTH_SHORT).show();

                    }/*else if(binding.mobileNo.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your Mobileno", Toast.LENGTH_SHORT).show();

                    }*/else if(imageDetails.equals("")){

                        Toast.makeText(getContext(), "Select Your Image", Toast.LENGTH_SHORT).show();

                    }else{

                        editBankDetails(userID,binding.bankName.getText().toString().trim(),
                                binding.bankAccountNo.getText().toString().trim(),
                                binding.userFullName.getText().toString().trim(),
                                binding.iFCSCode.getText().toString().trim(),imageDetails);
                    }
                }


            }
        });

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

                    imageDetails = getEncodedString(bitmap);
                    String[] projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(uri,
                            projection, null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String selectedImagePath = cursor.getString(column_index);
                    String imagedetails = getByteArrayFromImageURL(selectedImagePath);

                   // Log.d("imageselect12","selectimg11  "+imageDetails);
                   Log.d("imageselect12","selectimg111  "+selectedImagePath);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Log.d("PhotoPicker", "No media selected");
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

        return binding.getRoot();


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
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext().getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    //chooseImage(getContext());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage = (Bitmap) data.getExtras().get("data");
                       // binding_bank.editShowImagePtah.setText(selectedImage.toString());
                        imageDetails = getEncodedString(selectedImage);
                        Log.d("imageselect","selectimg1  "+selectedImage);
                    }
                    break;
            }
        }
    }

    private String getEncodedString(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageBytes = baos.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private String getByteArrayFromImageURL(String url) {

        URL newurl;
        Bitmap bitmap;
        String base64 = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }
    private String getFilePathFromContentUri(Uri selectedVideoUri,
                                             ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    public void addBankDetails(String uid, String bankName, String accountNo, String accountHolder, String ifsc, String file){

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("uid",uid);
            jsonObject.put("bankName",bankName);
            jsonObject.put("accountNo",accountNo);
            jsonObject.put("accountHolder",accountHolder);
            jsonObject.put("ifsc",ifsc);
            jsonObject.put("file",file);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Log.d("userdetailsDta",jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.add_user_bank_account, jsonObject, new Response.Listener<JSONObject>() {
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

    public void addBankAccountDetails() {

        //Show Your Another AlertDialog
        final Dialog dialog = new Dialog(getActivity());
        binding_bank = BankaccountFragmentBinding.inflate(LayoutInflater.from(getContext()));
        dialog.setContentView(binding_bank.getRoot());
        dialog.setCancelable(false);

//        binding_bank.btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(getActivity(),
//                            Manifest.permission.CAMERA)
//
//                            != PackageManager.PERMISSION_GRANTED) {
//
//                        // Should we show an explanation?
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                                Manifest.permission.CAMERA)) {
//                            showAlert();
//                        } else {
//                            // No explanation needed, we can request the permission.
//                            ActivityCompat.requestPermissions(getActivity(),
//                                    new String[]{Manifest.permission.CAMERA},
//                                    MY_PERMISSIONS_REQUEST_CAMERA);
//                        }
//                    } else {
//
//                        showSettingsAlert();
//                    }
//                } else {
//
//                    chooseImage(getActivity());
//                }
//
////                if (checkAndRequestPermissions(getActivity())) {
////                    chooseImage(getActivity());
////                }
//            }
//        });
        binding_bank.btnSaveBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding_bank.editUserFullName.getText().toString().trim().equals("")){

                    Toast.makeText(getContext(), "Enter Your Name", Toast.LENGTH_SHORT).show();

                }else if(binding_bank.editBankName.getText().toString().trim().equals("")){

                    Toast.makeText(getContext(), "Enter Your Bank Name", Toast.LENGTH_SHORT).show();

                }else if(binding_bank.editBankAccountNo.getText().toString().trim().equals("")){

                    Toast.makeText(getContext(), "Enter Your Bank AccountNo", Toast.LENGTH_SHORT).show();

                }else if(binding_bank.editIFCSCode.getText().toString().trim().equals("")){

                    Toast.makeText(getContext(), "Enter Your IFCSCode", Toast.LENGTH_SHORT).show();

                }else if(binding_bank.editMobileNo.getText().toString().trim().equals("")){

                    Toast.makeText(getContext(), "Enter Your Mobileno", Toast.LENGTH_SHORT).show();

                }/*else if(imageDetails.equals("")){

                    Toast.makeText(getContext(), "Select Your Image", Toast.LENGTH_SHORT).show();

                }*/else{

                    addBankDetails(userID,binding_bank.editBankName.getText().toString().trim(),
                            binding_bank.editBankAccountNo.getText().toString().trim(),
                            binding_bank.editUserFullName.getText().toString().trim(),
                            binding_bank.editIFCSCode.getText().toString().trim(),imageDetails);
                }
            }
        });
        binding_bank.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.drawable.homecard_back1);

    }

    public void viewBankAccoutDetails(String userId){

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userId",userId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.user_bank_dtls, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressbar.hideDialog();

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("202")){

                        binding.linBankdetails.setVisibility(View.GONE);
                        binding.linInsertbankdetails.setVisibility(View.VISIBLE);

                        String user_details = response.getString("user_details");

                        JSONObject jsonObject_user_details = new JSONObject(user_details);

                        String userBannk_id = jsonObject_user_details.getString("userBannk_id");
                        String userBannk_user = jsonObject_user_details.getString("userBannk_user");
                        String userBannk_bank = jsonObject_user_details.getString("userBannk_bank");
                        String userBannk_accountNo = jsonObject_user_details.getString("userBannk_accountNo");
                        String userBannk_accountHolder = jsonObject_user_details.getString("userBannk_accountHolder");
                        String userBannk_IFSC = jsonObject_user_details.getString("userBannk_IFSC");
                        String userBannk_file = jsonObject_user_details.getString("userBannk_file");

                        String image = "https://projects.conjuror.in/investor/"+userBannk_file;
                        Picasso.with(getContext()).load(image).into(binding.showbankImage);
                       // binding.showbankImage.animate().rotation(90).start();

                        binding.bankAccountNo.setText(userBannk_accountNo);
                        binding.bankName.setText(userBannk_bank);
                        binding.userFullName.setText(userBannk_accountHolder);
                        binding.iFCSCode.setText(userBannk_IFSC);

                    }else{

                        binding.linBankdetails.setVisibility(View.VISIBLE);
                        binding.linUploadbankdetails.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Bank details not found", Toast.LENGTH_SHORT).show();
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
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }

    public void editBankDetails(String uid, String bankName, String accountNo, String accountHolder, String ifsc, String file){

        progressbar.showDialog();

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("uid",uid);
            jsonObject.put("bankName",bankName);
            jsonObject.put("accountNo",accountNo);
            jsonObject.put("accountHolder",accountHolder);
            jsonObject.put("ifsc",ifsc);
            //jsonObject.put("file",file);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Log.d("userdetailsDta",jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiList.edit_bank_dtls, jsonObject, new Response.Listener<JSONObject>() {
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
