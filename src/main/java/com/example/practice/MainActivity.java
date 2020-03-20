package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText TextInputEditText_email;
    TextInputEditText TextInputEditText_password;
    RelativeLayout RelativeLayout_login;
    String emailOK="123@gmail.com";
    String passwordOK="1234";

    String inputEmail=""; //사용자가 입력한 email
    String inputPassword=""; //사용자가 입력한 password

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText_email     =findViewById(R.id.TextInputEditText_email);
        TextInputEditText_password  =findViewById(R.id.TextInputEditText_password);
        RelativeLayout_login        =findViewById(R.id.RelativeLayout_login);

        //1.값을 가져온다 - 검사 (123@gmail.com / 1234) 인 경우에만
        //2.클륵을 감지한다
        //3.1번의 값을 다음 액티비티로 넘긴다
        RelativeLayout_login.setClickable(false);
        TextInputEditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //글자가 바뀔때

                if(s!=null){
                    Log.d("SENTI",s.toString());
                    inputEmail=s.toString();
                    RelativeLayout_login.setClickable(validation());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextInputEditText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //글자가 바뀔때
                if(s!=null) {
                    Log.d("SENTI", s.toString());
                    inputPassword=s.toString();
                    RelativeLayout_login.setClickable(validation());
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       // RelativeLayout_login.setClickable(true);
        RelativeLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override //clicklistener은 이것
            public void onClick(View v) {
                //click을 감지하면 가져옴
                Log.d("SENTI", "onClick");

                String email=TextInputEditText_email.getText().toString(); //가져오고 string으로 바꿔줌
                String password=TextInputEditText_password.getText().toString();

                Intent intent=new Intent(MainActivity.this,LoginResultActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });

    }

    public boolean validation(){
        Log.d("SENTI", inputEmail+"/"+inputPassword+"/"+(inputEmail.equals(emailOK))+"/"+(inputPassword.equals(passwordOK)));
        return inputEmail.equals(emailOK) && inputPassword.equals(passwordOK);
    }
}
