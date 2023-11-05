package com.tanvir.tt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tanvir.tt.R;
import com.tanvir.tt.admin.AdminHomeActivity;
import com.tanvir.tt.user.HomeCategoryActivity;
import com.tanvir.tt.user.SeclectionTicketActivity;

import next.firenext.NextDatabase;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentUser != null)
                {
                    String condition = NextDatabase.read(getApplicationContext(),"user>condition:");
                    if (condition.equals("owner"))
                    {
                        Intent intent = new Intent(SplashActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(SplashActivity.this, HomeCategoryActivity.class);
                        intent.putExtra("guest","false");
                        startActivity(intent);
                        finish();
                    }


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