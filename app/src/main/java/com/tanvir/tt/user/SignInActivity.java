package com.tanvir.tt.user;

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

public class SignInActivity extends AppCompatActivity {

    EditText email, password;

    TextView forgotPassword;
    ImageView backBtn;
    Button user_signup, signIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);
        signIn = findViewById(R.id.sign_in_btn);
        forgotPassword = findViewById(R.id.sign_in_forgotPassword);


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
                                NextDatabase.add(getApplicationContext(),"user>condition:customer");
                                Intent intent = new Intent(SignInActivity.this, HomeCategoryActivity.class);
                                intent.putExtra("guest","false");
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
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });



        backBtn = findViewById(R.id.sign_in_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        user_signup = findViewById(R.id.sign_in_sign_up_btn);
        user_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}