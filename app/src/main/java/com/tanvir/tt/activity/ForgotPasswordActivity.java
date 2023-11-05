package com.tanvir.tt.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tanvir.tt.R;

public class ForgotPasswordActivity extends AppCompatActivity {


    ImageView backBtn;
    EditText email;
    Button emailCheck;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.forgot_password);
        emailCheck = findViewById(R.id.email_check);



        // --------- back btn ----------//
        backBtn = findViewById(R.id.forgot_password_back_btn);
        forBackBtn(backBtn);

        emailCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().equals(""))
                {
                    resetPassword();
                }
                else
                {
                    email.setHint("Write your registered email");
                    email.setHintTextColor(Color.argb(100, 84, 95, 133));
                }

            }
        });
    }

    private void resetPassword() {


        firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(ForgotPasswordActivity.this,"Please Check Your Email Box", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(ForgotPasswordActivity.this,"Email not registered", Toast.LENGTH_SHORT).show();
                        }



                    }
                });
    }

    private void forBackBtn(ImageView backBtn) {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}