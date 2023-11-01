package com.tanvir.tt.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanvir.tt.R;
import com.tanvir.tt.user.HomeActivity;
import com.tanvir.tt.user.SignUpActivity;

import java.util.HashMap;
import java.util.Objects;

public class AdminRegistrationActivity extends AppCompatActivity {

    ImageView backBtn;
    EditText adminName, companyName, email, phone, password, authorizesKey;
    Button signUpBtn;

    FirebaseAuth mAuth;
    private String currentUser;
    DatabaseReference userReference, authReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        mAuth = FirebaseAuth.getInstance();
        userReference = FirebaseDatabase.getInstance().getReference().child("All Company").child("Company");
        authReference = FirebaseDatabase.getInstance().getReference().child("VerifiedKey");


        adminName = findViewById(R.id.admin_owner_name);
        companyName = findViewById(R.id.admin_company_name);
        email = findViewById(R.id.admin_email);
        phone = findViewById(R.id.admin_phone_number);
        password = findViewById(R.id.admin_password);
        authorizesKey = findViewById(R.id.admin_confirm_password);
        signUpBtn = findViewById(R.id.admin_create_account_btn);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authReference.child("key").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if (snapshot.exists())
                        {
                            String key1 = Objects.requireNonNull(snapshot.child("key1").getValue()).toString();

                            if (authorizesKey.getText().toString().equals(key1))
                            {
                                authReference.child("key").child("key1").setValue("set_key");
                                if (!adminName.getText().toString().equals("") && !companyName.getText().toString().equals("") && !email.getText().toString().equals("") && !phone.getText().toString().equals("") && !password.getText().toString().equals(""))
                                {
                                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful())
                                            {

                                                currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                                                HashMap<String, Object> map = new HashMap<String, Object>();
                                                map.put("name", adminName.getText().toString());
                                                map.put("company", companyName.getText().toString());
                                                map.put("email", email.getText().toString());
                                                map.put("phone", phone.getText().toString());
                                                map.put("password", password.getText().toString());

                                                userReference.child(currentUser).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            Intent intent = new Intent(AdminRegistrationActivity.this, AdminHomeActivity.class);
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
                            else
                            {
                                authorizesKey.setText("");
                                Toast.makeText(AdminRegistrationActivity.this,"Wrong Key", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });




        backBtn = findViewById(R.id.admin_registration_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}