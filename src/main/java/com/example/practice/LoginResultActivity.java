package com.example.practice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginResultActivity extends AppCompatActivity {

    TextView TextView_get;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView_get=findViewById(R.id.TextView_get);

        Intent intent=getIntent(); //받을 인텐트

        //값가져오기
        Bundle bundle = intent.getExtras();  //extra로 보냈으니, 받을때도 extra로 받아야함
        String email = bundle.getString("email");
        String password = bundle.getString("password");

        TextView_get.setText(email+" / "+password);
    }
}
