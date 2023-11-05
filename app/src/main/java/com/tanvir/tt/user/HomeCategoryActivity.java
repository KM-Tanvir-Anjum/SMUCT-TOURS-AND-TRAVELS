package com.tanvir.tt.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tanvir.tt.R;

public class HomeCategoryActivity extends AppCompatActivity {

    TextView profile, buyTicket, myTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_category);

        profile = findViewById(R.id.profile);
        buyTicket = findViewById(R.id.buy_ticket);
        myTicket = findViewById(R.id.my_ticket);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCategoryActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
        buyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCategoryActivity.this, SeclectionTicketActivity.class);
                intent.putExtra("guest","false");
                startActivity(intent);
            }
        });
        myTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCategoryActivity.this, MyTicketActivity.class);
                startActivity(intent);
            }
        });
    }
}