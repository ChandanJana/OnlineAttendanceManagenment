package com.example.chandan.theprofessional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by SUBHADIP on 10-09-2017.
 */

public class ForgetPass extends AppCompatActivity {
    EditText Uid1, AltEmail1;
    Button GetPass1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass);
        Uid1 = findViewById(R.id.Uid);
        AltEmail1 = findViewById(R.id.AltEmail);
        GetPass1 = findViewById(R.id.GetPass);
    }
}
