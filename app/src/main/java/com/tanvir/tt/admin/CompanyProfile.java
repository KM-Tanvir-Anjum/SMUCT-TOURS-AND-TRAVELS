package com.tanvir.tt.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanvir.tt.R;

import java.util.Objects;

public class CompanyProfile extends AppCompatActivity {

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    String currentUser;

    TextView companyName, email, phoneNumber;

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("All Company");

        companyName = findViewById(R.id.company_name);
        email = findViewById(R.id.company_email);
        phoneNumber = findViewById(R.id.company_number);
        backBtn = findViewById(R.id.company_profile_backBtn);


        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("company").exists()) {
                        companyName.setText(Objects.requireNonNull(snapshot.child("company").getValue()).toString());
                    }
                    if (snapshot.child("email").exists()) {
                        email.setText(Objects.requireNonNull(snapshot.child("email").getValue()).toString());
                    }
                    if (snapshot.child("phone").exists()) {
                        phoneNumber.setText(Objects.requireNonNull(snapshot.child("phone").getValue()).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
//    child("All Company").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                if (snapshot.exists())
//                {
//                    companyName.setText(Objects.requireNonNull(snapshot.child("company").getValue()).toString());
//                    email.setText(Objects.requireNonNull(snapshot.child("email").getValue()).toString());
//                    phoneNumber.setText(Objects.requireNonNull(snapshot.child("phone").getValue()).toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}