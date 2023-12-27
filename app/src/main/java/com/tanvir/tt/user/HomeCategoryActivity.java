package com.tanvir.tt.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tanvir.tt.R;
import com.tanvir.tt.activity_faq;

public class HomeCategoryActivity extends AppCompatActivity {

    TextView profile, buyTicket, myTicket,faq,advanceSearch,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_category);

        profile = findViewById(R.id.profile);
        buyTicket = findViewById(R.id.buy_ticket);
        myTicket = findViewById(R.id.my_ticket);
        faq=findViewById(R.id.faq);
        feedback=findViewById(R.id.feedback);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCategoryActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCategoryActivity.this, FeedbackActivity.class);
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

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCategoryActivity.this, activity_faq.class);
                startActivity(intent);
            }
        });
    }
}