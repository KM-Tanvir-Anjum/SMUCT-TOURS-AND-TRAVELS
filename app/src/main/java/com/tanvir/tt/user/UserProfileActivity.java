package com.tanvir.tt.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.tanvir.tt.activity.MainActivity;

import java.util.Objects;

import next.firenext.NextDatabase;

public class UserProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    String currentUser;

    TextView Name, email, phoneNumber, logOut;

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");

        Name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        phoneNumber = findViewById(R.id.user_number);
        backBtn = findViewById(R.id.user_profile_backBtn);
        logOut = findViewById(R.id.user_profile_logout);


//        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                if (snapshot.exists())
//                {
//                    Name.setText(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
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

        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("name").exists()) {
                        Name.setText(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
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

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                NextDatabase.delete(getApplicationContext(),"user:");
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}