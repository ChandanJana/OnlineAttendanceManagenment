package com.example.chandan.theprofessional;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class NewUser extends AppCompatActivity {
    private EditText userName, userEmail, userPassword, userConfPassword, userAltEmail, userAddress, userPhone;
    private Button signUpBn, uploadImageBn;
    private ImageView imageView1;
    final static int SELECT_PICTURE = 100;
    Bitmap bitmap = null;
    private String user_name, user_email, user_password, user_conf_pass, user_alt_email,
            user_address, user_phone, user_image = null;
    private final static String KEY_USER_NAME = "name";
    private final static String KEY_USER_EMAIL = "email";
    private final static String KEY_USER_PASS = "pass";
    private final static String KEY_USER_ALTMAIL = "altmail";
    private final static String KEY_USER_ADD = "addr";
    private final static String KEY_USER_PHNO = "phno";
    private final static String KEY_USER_IMG = "image";
    private final static String UPLOAD_URL = "http://192.168.137.1/studentapi/UserUpload.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        userName = findViewById(R.id.name);
        userEmail = findViewById(R.id.Email);
        userPassword = findViewById(R.id.UserPass);
        userConfPassword = findViewById(R.id.ConUserPass);
        userAltEmail = findViewById(R.id.AltEmail);
        userAddress = findViewById(R.id.Addr);
        userPhone = findViewById(R.id.PhNo);
        imageView1 = findViewById(R.id.ImgVw);
        uploadImageBn = findViewById(R.id.UpImage);
        signUpBn = findViewById(R.id.SignIn);
        signUpBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserData();
            }
        });
        uploadImageBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
    }

    private void addUserData() {
        user_name = userName.getText().toString().trim();
        user_email = userEmail.getText().toString().trim();
        user_password = userPassword.getText().toString().trim();
        user_conf_pass = userConfPassword.getText().toString().trim();
        user_alt_email = userAltEmail.getText().toString().trim();
        user_address = userAddress.getText().toString().trim();
        user_phone = userPhone.getText().toString().trim();
        user_image = getStringImage(bitmap);
        if (user_name.equals("") || user_name.equals(" ") || user_email.equals("")
                || user_email.equals(" ") || user_password.equals("")
                || user_conf_pass.equals("") || user_alt_email.equals("")
                || user_alt_email.equals(" ") || user_address.equals("")
                || user_address.equals(" ") || user_phone.equals("")
                || user_phone.equals(" ")) {
            Toast.makeText(NewUser.this, "Enter all the fields", Toast.LENGTH_LONG).show();
        } else {
            if (user_phone.length() < 10 || user_phone.length() > 10) {
                Toast.makeText(NewUser.this, "Invalid mobile number", Toast.LENGTH_LONG).show();
            } else {
                if (user_password.equals(user_conf_pass) == false) {
                    Toast.makeText(NewUser.this, "Password is not same", Toast.LENGTH_LONG).show();
                } else {
                    if (user_email.equals(user_alt_email)) {
                        Toast.makeText(NewUser.this, "Email and alter Email can't be same", Toast.LENGTH_LONG).show();
                    } else {
                        if (user_image == null) {
                            user_image = "";
                        }
                        StringRequest req = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.toString().equals("1")) {
                                    Intent intent = new Intent(NewUser.this, Login.class);
                                    startActivity(intent);
                                    Toast.makeText(NewUser.this, "Registration successful", Toast.LENGTH_LONG).show();
                                    NewUser.this.finish();
                                } else {
                                    Toast.makeText(NewUser.this, "Registration failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(NewUser.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new Hashtable<String, String>();
                                params.put(KEY_USER_NAME, user_name);
                                params.put(KEY_USER_EMAIL, user_email);
                                params.put(KEY_USER_PASS, user_password);
                                params.put(KEY_USER_ALTMAIL, user_alt_email);
                                params.put(KEY_USER_ADD, user_address);
                                params.put(KEY_USER_PHNO, user_phone);
                                params.put(KEY_USER_IMG, user_image);
                                return params;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(NewUser.this);
                        queue.add(req);
                    }
                }
            }
        }
    }

    private String getStringImage(Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
        byte[] imgfile = byteArray.toByteArray();
        String EncodeIng = Base64.encodeToString(imgfile, Base64.DEFAULT);
        return EncodeIng;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePathHeader = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(NewUser.this.getContentResolver(), filePathHeader);
                imageView1.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(NewUser.this, "Image loading problem", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // To hide keypad touch any where of outside the editText.
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handleReturn = super.dispatchTouchEvent(ev);
        View view = getCurrentFocus();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (view instanceof EditText) {
            View innerView = getCurrentFocus();

            if (ev.getAction() == MotionEvent.ACTION_UP && !getLocationOnScreen((EditText) innerView).contains(x, y)) {

                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return handleReturn;
    }

    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];
        mEditText.getLocationOnScreen(location);
        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();
        return mRect;
    }
}
