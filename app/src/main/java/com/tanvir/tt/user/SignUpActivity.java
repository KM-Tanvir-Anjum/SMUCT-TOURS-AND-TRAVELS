package com.tanvir.tt.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tanvir.tt.R;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ImageView backBtn;
    EditText firstName, lastName, email, phone, password, confirmPassword;
    Button signUpBtn;

    FirebaseAuth mAuth;
    private String currentUser;
    DatabaseReference userReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        userReference = FirebaseDatabase.getInstance().getReference().child("Customer");


        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.sign_up_email);
        phone = findViewById(R.id.sign_up_phone_number);
        password = findViewById(R.id.sign_up_password);
        confirmPassword = findViewById(R.id.sign_up_confirm_password);
        signUpBtn = findViewById(R.id.create_account_btn);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") && !email.getText().toString().equals("") && !phone.getText().toString().equals("") && !password.getText().toString().equals("") && confirmPassword.getText().toString().equals(password.getText().toString()) )
                {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {

                                currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("name", firstName.getText().toString()+" "+lastName.getText().toString());
                                map.put("email", email.getText().toString());
                                map.put("phone", phone.getText().toString());
                                map.put("password", password.getText().toString());

                                userReference.child(currentUser).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                            intent.putExtra("guest","false");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });

                            }

                        }
                    });
                }
            }
        });




        backBtn = findViewById(R.id.signup_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}