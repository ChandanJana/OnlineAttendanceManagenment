package com.example.chandan.theprofessional;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chandan.theprofessional.Key.Key;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddStudentRecord extends Fragment {
    private EditText sName, sEmail, sAddress, sPhone, sClass, sBatch, sFees;
    private ImageView imageView;
    private Button sUploadBn, sSubmitBn;
    private final static int SELECT_PICTURE = 100;
    private Bitmap bitmap = null;
    private ProgressDialog dialog = null;
    private String studentName, studentEmail, studentAddress, studentPhone, studentClass,
            studentBatch, studentFee, studentImage = null, UId;
    private final static String KEY_STU_NAME = "name";
    private final static String KEY_STU_CLASS = "class";
    private final static String KEY_STU_MOBILE = "mobile";
    private final static String KEY_STU_FEE = "fee";
    private final static String KEY_STU_ADD = "add";
    private final static String KEY_STU_IMAGE = "image";
    private final static String KEY_STU_EMAIL = "email";
    private final static String KEY_STU_BATCH = "batch";
    private final static String KEY_USER_EMAIL = "Uemail";
    private final static String UPLOAD_URL = "http://192.168.137.1/studentapi/studentsUpload.php";

    public AddStudentRecord() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_student_rec, container, false);
        sName = view.findViewById(R.id.Sname);
        sEmail = view.findViewById(R.id.Email);
        sAddress = view.findViewById(R.id.Addr);
        sPhone = view.findViewById(R.id.PhNo);
        sClass = view.findViewById(R.id.ClasS);
        sBatch = view.findViewById(R.id.Batch);
        sFees = view.findViewById(R.id.Fees);
        imageView = view.findViewById(R.id.ImgVw);
        sUploadBn = view.findViewById(R.id.UpImage);
        sSubmitBn = view.findViewById(R.id.AddStu);
        sSubmitBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStuData();
            }
        });
        sUploadBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
        return view;
    }

    private void AddStuData() {
        studentName = sName.getText().toString().trim();
        studentEmail = sEmail.getText().toString().trim();
        studentAddress = sAddress.getText().toString().trim();
        studentPhone = sPhone.getText().toString().trim();
        studentClass = sClass.getText().toString().trim();
        studentBatch = sBatch.getText().toString().trim();
        studentFee = sFees.getText().toString().trim();
        studentImage = getStringImage(bitmap);
        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = share.edit();
        final String Uemail = share.getString(Key.KEY_USER_EMAIL, "");
        if (studentName.equals("") || studentName.equals(" ") || studentEmail.equals("") || studentEmail.equals(" ") || studentAddress.equals("") || studentAddress.equals(" ")
                || studentPhone.equals("") || studentPhone.equals(" ") || studentClass.equals("") || studentClass.equals(" ") || studentBatch.equals("") || studentBatch.equals(" ") || studentFee.equals("") || studentFee.equals(" ")) {
            Toast.makeText(getContext(), "PLz enter the fields", Toast.LENGTH_LONG).show();
        } else {
            if (studentPhone.length() < 10 || studentPhone.length() > 10) {
                Toast.makeText(getContext(), "Invalid mobile number", Toast.LENGTH_LONG).show();
            } else {
                if (studentImage == null) {
                    studentImage = "";
                }
                dialog = new ProgressDialog(getContext());
                dialog.setMessage("please wait");
                dialog.setCancelable(false);
                dialog.setIndeterminate(false);
                dialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                        sName.setText("");
                        sEmail.setText("");
                        sAddress.setText("");
                        sPhone.setText("");
                        sClass.setText("");
                        sBatch.setText("");
                        sFees.setText("");
                        imageView.setImageResource(R.drawable.user);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), String.valueOf(error), Toast.LENGTH_LONG).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new Hashtable<String, String>();
                        params.put(KEY_STU_NAME, studentName);
                        params.put(KEY_STU_EMAIL, studentEmail);
                        params.put(KEY_STU_ADD, studentAddress);
                        params.put(KEY_STU_MOBILE, studentPhone);
                        params.put(KEY_STU_CLASS, studentClass);
                        params.put(KEY_STU_BATCH, studentBatch);
                        params.put(KEY_STU_FEE, studentFee);
                        params.put(KEY_STU_IMAGE, studentImage);
                        params.put(KEY_USER_EMAIL, Uemail);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(request);
            }
        }
    }

    public String getStringImage(Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
        byte[] imageFile = byteArray.toByteArray();
        String EncodeIng = Base64.encodeToString(imageFile, Base64.DEFAULT);
        return EncodeIng;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePathHeader = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePathHeader);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Image loading problem", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}