package com.example.chandan.theprofessional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chandan.theprofessional.Key.Key;
import com.example.chandan.theprofessional.Parse.Parse_User;

import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

public class Login extends AppCompatActivity {
    private EditText loginUserId, loginPassword;
    private Button loginBn;
    private TextView forgetPasswordText, newAccountText;
    private String user_id, user_pass;
    private final static String KEY_USER_NAME = "id";
    private final static String KEY_USER_PASS = "pass";
    private final static String UPLOAD_URL = "http://192.168.137.1/studentapi/teacherLogin.php";
    private static Parse_User user = null;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginUserId = findViewById(R.id.UserID);
        loginPassword = findViewById(R.id.Password);
        loginBn = findViewById(R.id.LogIn);
        forgetPasswordText = findViewById(R.id.ForPass);
        newAccountText = findViewById(R.id.NewAcc);
        forgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Login.this, ForgetPass.class);
                startActivity(intent);
            }
        });
        newAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Login.this, NewUser.class);
                startActivity(intent);
            }
        });
        loginBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_user();
            }
        });
    }

    private void login_user() {
        user_id = loginUserId.getText().toString().trim();
        user_pass = loginPassword.getText().toString().trim();
        if (user_id.equals("") || user_id.equals(" ") || user_pass.equals("")) {
            Toast.makeText(Login.this, "Enter all the fields", Toast.LENGTH_LONG).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    StringTokenizer tokenizer = new StringTokenizer(response, "-");
                    String[] tech = new String[tokenizer.countTokens()];
                    int i = 0;
                    while (tokenizer.hasMoreTokens()) {
                        tech[i] = tokenizer.nextToken();
                        i++;
                    }
                    user = new Parse_User(tech[0]);
                    user.getDataFromJson();
                    if (tech[1].toString().equals("1")) {
                        intent = new Intent(Login.this, Welcome.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Login.this, String.valueOf("Welcome " + user.uName[0]), Toast.LENGTH_LONG).show();
                        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getApplication());
                        SharedPreferences.Editor editor = share.edit();
                        editor.putString(Key.KEY_USER_NAME, user.uName[0]);
                        editor.putString(Key.KEY_USER_EMAIL, user.uEmail[0]);
                        editor.putString(Key.KEY_USER_ALTEMAIL, user.uAltmail[0]);
                        editor.putString(Key.KEY_USER_ADD, user.uAdd[0]);
                        editor.putString(Key.KEY_USER_PHNO, user.uPhno[0]);
                        editor.putString(Key.KEY_USER_IMAGE, user.uImage[0]);
                        editor.apply();
                        Login.this.finish();
                    } else {
                        Toast.makeText(Login.this, "Invalid User id or Password", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put(KEY_USER_NAME, user_id);
                    params.put(KEY_USER_PASS, user_pass);
                    return params;
                }

            };
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(req);
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
