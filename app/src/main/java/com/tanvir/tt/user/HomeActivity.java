package com.tanvir.tt.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanvir.tt.R;
import com.tanvir.tt.activity.MainActivity;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    LinearLayout userLayout, guestLayout;
    TextView name, email, phone, forLogIn, logOutBtn;
    private String currentUser, guest = "false";
    FirebaseAuth mAuth;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        guest = getIntent().getStringExtra("guest");



        userLayout = findViewById(R.id.profile_layout);
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        phone = findViewById(R.id.profile_number);
        logOutBtn = findViewById(R.id.log_out_btn);


        guestLayout = findViewById(R.id.guest_layout);
        forLogIn = findViewById(R.id.home_log_in_your_account);
        forLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (currentUser == null)
        {
            userLayout.setVisibility(View.GONE);
            guestLayout.setVisibility(View.VISIBLE);
            logOutBtn.setVisibility(View.GONE);
        }
        if (guest.equals("false"))
        {
            userLayout.setVisibility(View.VISIBLE);
            guestLayout.setVisibility(View.GONE);
            logOutBtn.setVisibility(View.VISIBLE);

            mAuth = FirebaseAuth.getInstance();
            currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            userReference = FirebaseDatabase.getInstance().getReference().child("Customer");
            userReference.child(currentUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists())
                    {
                        name.setText(snapshot.child("name").getValue().toString());
                        email.setText(snapshot.child("email").getValue().toString());
                        phone.setText(snapshot.child("phone").getValue().toString());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}