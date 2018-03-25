package com.example.chandan.theprofessional;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chandan.theprofessional.Key.Key;

/**
 * Created by SUBHADIP on 18-10-2017.
 */

public class Navigate extends AppCompatActivity {
    private ImageView imageView;
    private TextView name1, email1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_welcome);
        //setContentView(R.layout.activity_welcome);
        imageView = findViewById(R.id.PrflImage);
        name1 = findViewById(R.id.UserName);
        email1 = findViewById(R.id.UserMail);
        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = share.edit();
        String userName = share.getString(Key.KEY_USER_NAME, "");
        String userEmail = share.getString(Key.KEY_USER_EMAIL, "");
        name1.setText(userName);
        email1.setText(userEmail);
    }
}
