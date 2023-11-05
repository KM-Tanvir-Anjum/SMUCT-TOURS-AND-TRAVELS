package com.tanvir.tt.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tanvir.tt.R;
import com.tanvir.tt.activity.ForgotPasswordActivity;

import next.firenext.NextDatabase;

public class AdminLogInActivity extends AppCompatActivity {

    ImageView backBtn;


    EditText email, password;

    TextView forgotPassword;
    Button admin_signup, signIn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log_in);

        mAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.admin_sign_in_email);
        password = findViewById(R.id.admin_sign_in_password);
        signIn = findViewById(R.id.admin_sign_in_btn);
        admin_signup = findViewById(R.id.admin_registration);
        forgotPassword = findViewById(R.id.admin_log_in_forgotPassword);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().equals("") && !password.getText().toString().equals(""))
                {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                NextDatabase.add(getApplicationContext(),"user>condition:owner");
                                Intent intent = new Intent(AdminLogInActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLogInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });



        backBtn = findViewById(R.id.admin_login_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        admin_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminLogInActivity.this, AdminRegistrationActivity.class);
                startActivity(intent);
            }
        });


    }
}