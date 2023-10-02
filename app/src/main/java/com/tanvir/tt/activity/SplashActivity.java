package com.tanvir.tt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tanvir.tt.R;
import com.tanvir.tt.user.HomeActivity;
import com.tanvir.tt.user.SignInActivity;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser CurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (CurrentUser != null)
                {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.putExtra("guest","false");
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }



            }
        }, 2000);
    }
}