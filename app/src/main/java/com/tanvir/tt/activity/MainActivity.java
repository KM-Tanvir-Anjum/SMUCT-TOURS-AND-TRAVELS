package com.tanvir.tt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tanvir.tt.R;
import com.tanvir.tt.admin.AdminLogInActivity;
import com.tanvir.tt.user.SignInActivity;

public class MainActivity extends AppCompatActivity {

    Button adminLoginBtn, userLogInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminLoginBtn = findViewById(R.id.admin_logIn);
        userLogInBtn = findViewById(R.id.user_logIn);


        adminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminLogInActivity.class);
                startActivity(intent);
            }
        });

        userLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


    }
}