package com.tanvir.tt.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tanvir.tt.Activity_User_Signup;
import com.tanvir.tt.R;

public class SignInActivity extends AppCompatActivity {

    ImageView backBtn;
    Button user_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        backBtn = findViewById(R.id.sign_in_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        user_signup = findViewById(R.id.user_sign_up);
        user_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this, Activity_User_Signup.class);
                startActivity(intent);
            }
        });
    }
}