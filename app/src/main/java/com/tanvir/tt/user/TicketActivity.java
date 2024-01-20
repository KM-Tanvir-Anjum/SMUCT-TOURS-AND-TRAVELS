package com.tanvir.tt.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanvir.tt.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class TicketActivity extends AppCompatActivity {

    LinearLayout a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4,e1,e2,e3,e4,f1,f2,f3,f4,g1,g2,g3,g4,g5;
    RelativeLayout amountLayout, purchaseLayout;
    TextView seatAndAmount, purchase;
    static String A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,D1,D2,D3,D4,E1,E2,E3,E4,F1,F2,F3,F4,G1,G2,G3,G4,G5 = "";
    String fromTo, DateTime, comapanyName;
    ImageView backBtn;

    public int seat;
    int seatPrice = 250;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef, databaseReference;

    //----------- for ticket pdf ----------//
    LinearLayout ticketLayout, downloadLayout;
    TextView from,to,date,time,contact,seatName,price, name, company;
    EditText passengerName, passengerContact;
    Button ticketDownloadBtn;
    String finalSeatName;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    String coachNo;
    String push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        //Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        fromTo = getIntent().getStringExtra("fromTo");
        DateTime = getIntent().getStringExtra("DateTime");
        comapanyName = getIntent().getStringExtra("companyName");
        //Toast.makeText(this, fromToDateTime, Toast.LENGTH_SHORT).show();
        DatabaseReference ticketRef = database.getReference("Bus Ticket").child(fromTo).child(DateTime).child(comapanyName);

        ticketLayout = findViewById(R.id.pasenger_Ticket_layout);

        amountLayout = findViewById(R.id.amount_relative_layout);
        purchaseLayout = findViewById(R.id.purchase_relative_layout);
        seatAndAmount = findViewById(R.id.seat_and_amount);
        purchase = findViewById(R.id.payment);
        backBtn = findViewById(R.id.back_btn);
        company = findViewById(R.id.name_of_company);
        company.setText(comapanyName);


        //-------------- for generate ticket -------------//

        downloadLayout = findViewById(R.id.pdf_ticket_layout);

        from = findViewById(R.id.from_t);
        to = findViewById(R.id.to_t);
        date = findViewById(R.id.date_t);
        time = findViewById(R.id.time_t);
        contact = findViewById(R.id.contact_t);
        name = findViewById(R.id.passenger_name);
        passengerName = findViewById(R.id.passenger_name_write);
        passengerContact = findViewById(R.id.passenger_contact_write);
        seatName = findViewById(R.id.seat_t);
        price = findViewById(R.id.price_t);
        ticketDownloadBtn = findViewById(R.id.download_btn);



        a1 = findViewById(R.id.A1);
        a2 = findViewById(R.id.A2);
        a3 = findViewById(R.id.A3);
        a4 = findViewById(R.id.A4);
        b1 = findViewById(R.id.B1);
        b2 = findViewById(R.id.B2);
        b3 = findViewById(R.id.B3);
        b4 = findViewById(R.id.B4);
        c1 = findViewById(R.id.C1);
        c2 = findViewById(R.id.C2);
        c3 = findViewById(R.id.C3);
        c4 = findViewById(R.id.C4);
        d1 = findViewById(R.id.D1);
        d2 = findViewById(R.id.D2);
        d3 = findViewById(R.id.D3);
        d4 = findViewById(R.id.D4);
        e1 = findViewById(R.id.E1);
        e2 = findViewById(R.id.E2);
        e3 = findViewById(R.id.E3);
        e4 = findViewById(R.id.E4);
        f1 = findViewById(R.id.F1);
        f2 = findViewById(R.id.F2);
        f3 = findViewById(R.id.F3);
        f4 = findViewById(R.id.F4);
        g1 = findViewById(R.id.G1);
        g2 = findViewById(R.id.G2);
        g3 = findViewById(R.id.G3);
        g4 = findViewById(R.id.G4);
        g5 = findViewById(R.id.G5);


        serverLoad(ticketRef);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TicketActivity.this,PaymentActivity.class);
            startActivity(intent);
            }
        });
        a1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (A1.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    a1.setBackgroundResource(R.drawable.red_chair);
                    A1 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!A1.equals("yes"))
                {
                    seat = seat+1;
                    A1 = ForSelect(seat, a1, seatPrice);
                }
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (A2.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    a2.setBackgroundResource(R.drawable.red_chair);
                    A2 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!A2.equals("yes"))
                {
                    seat = seat+1;
                    A2 = ForSelect(seat, a2, seatPrice);
                }

            }
        });

        a3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (A3.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    a3.setBackgroundResource(R.drawable.red_chair);
                    A3 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!A3.equals("yes"))
                {
                    seat = seat+1;
                    A3 = ForSelect(seat, a3, seatPrice);
                }

            }
        });

        a4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (A4.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    a4.setBackgroundResource(R.drawable.red_chair);
                    A4 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!A4.equals("yes"))
                {
                    seat = seat+1;
                    A4 = ForSelect(seat, a4, seatPrice);
                }

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (B1.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    b1.setBackgroundResource(R.drawable.red_chair);
                    B1 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!B1.equals("yes"))
                {
                    seat = seat+1;
                    B1 = ForSelect(seat, b1, seatPrice);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (B2.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    b2.setBackgroundResource(R.drawable.red_chair);
                    B2 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!B2.equals("yes"))
                {
                    seat = seat+1;
                    B2 = ForSelect(seat, b2, seatPrice);
                }

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (B3.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    b3.setBackgroundResource(R.drawable.red_chair);
                    B3 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!B3.equals("yes"))
                {
                    seat = seat+1;
                    B3 = ForSelect(seat, b3, seatPrice);
                }

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (B4.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    b4.setBackgroundResource(R.drawable.red_chair);
                    B4 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!B4.equals("yes"))
                {
                    seat = seat+1;
                    B4 = ForSelect(seat, b4, seatPrice);
                }

            }
        });


        c1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (C1.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    c1.setBackgroundResource(R.drawable.red_chair);
                    C1 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!C1.equals("yes"))
                {
                    seat = seat+1;
                    C1 = ForSelect(seat, c1, seatPrice);
                }
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (C2.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    c2.setBackgroundResource(R.drawable.red_chair);
                    C2 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!C2.equals("yes"))
                {
                    seat = seat+1;
                    C2 = ForSelect(seat, c2, seatPrice);
                }

            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (C3.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    c3.setBackgroundResource(R.drawable.red_chair);
                    C3 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!C3.equals("yes"))
                {
                    seat = seat+1;
                    C3 = ForSelect(seat, c3, seatPrice);
                }

            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (C4.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    c4.setBackgroundResource(R.drawable.red_chair);
                    C4 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!C4.equals("yes"))
                {
                    seat = seat+1;
                    C4 = ForSelect(seat, c4, seatPrice);
                }

            }
        });


        d1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (D1.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    d1.setBackgroundResource(R.drawable.red_chair);
                    D1 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!D1.equals("yes"))
                {
                    seat = seat+1;
                    D1 = ForSelect(seat, d1, seatPrice);
                }
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (D2.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    d2.setBackgroundResource(R.drawable.red_chair);
                    D2 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!D2.equals("yes"))
                {
                    seat = seat+1;
                    D2 = ForSelect(seat, d2, seatPrice);
                }
            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (D3.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    d3.setBackgroundResource(R.drawable.red_chair);
                    D3 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!D3.equals("yes"))
                {
                    seat = seat+1;
                    D3 = ForSelect(seat, d3, seatPrice);
                }
            }
        });

        d4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (D4.equals("Select"))
                {
                    seat = seat-1;
                    seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
                    purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
                    d4.setBackgroundResource(R.drawable.red_chair);
                    D4 = "";
                    ForPurchaseLayoutOpen(seat);
                }
                else if (!D4.equals("yes"))
                {
                    seat = seat+1;
                    D4 = ForSelect(seat, d4, seatPrice);
                }
            }
        });


        e1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (E1.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    e1.setBackgroundResource(R.drawable.red_chair);
                    E1 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!E1.equals("yes")) {
                    seat = seat + 1;
                    E1 = ForSelect(seat, e1, seatPrice);
                }
            }
        });


        e2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (E2.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    e2.setBackgroundResource(R.drawable.red_chair);
                    E2 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!E2.equals("yes")) {
                    seat = seat + 1;
                    E2 = ForSelect(seat, e2, seatPrice);
                }
            }
        });


        e3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (E3.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    e3.setBackgroundResource(R.drawable.red_chair);
                    E3 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!E3.equals("yes")) {
                    seat = seat + 1;
                    E3 = ForSelect(seat, e3, seatPrice);
                }
            }
        });


        e4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (E4.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    e4.setBackgroundResource(R.drawable.red_chair);
                    E4 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!E4.equals("yes")) {
                    seat = seat + 1;
                    E4 = ForSelect(seat, e4, seatPrice);
                }
            }
        });



        f1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (F1.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    f1.setBackgroundResource(R.drawable.red_chair);
                    F1 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!F1.equals("yes")) {
                    seat = seat + 1;
                    F1 = ForSelect(seat, f1, seatPrice);
                }
            }
        });

        f2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (F2.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    f2.setBackgroundResource(R.drawable.red_chair);
                    F2 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!F2.equals("yes")) {
                    seat = seat + 1;
                    F2 = ForSelect(seat, f2, seatPrice);
                }
            }
        });

        f3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (F3.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    f3.setBackgroundResource(R.drawable.red_chair);
                    F3 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!F3.equals("yes")) {
                    seat = seat + 1;
                    F3 = ForSelect(seat, f3, seatPrice);
                }
            }
        });

        f4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (F4.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    f4.setBackgroundResource(R.drawable.red_chair);
                    F4 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!F4.equals("yes")) {
                    seat = seat + 1;
                    F4 = ForSelect(seat, f4, seatPrice);
                }
            }
        });

        g1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (G1.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    g1.setBackgroundResource(R.drawable.red_chair);
                    G1 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!G1.equals("yes")) {
                    seat = seat + 1;
                    G1 = ForSelect(seat, g1, seatPrice);
                }
            }
        });

        g2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (G2.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    g2.setBackgroundResource(R.drawable.red_chair);
                    G2 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!G2.equals("yes")) {
                    seat = seat + 1;
                    G2 = ForSelect(seat, g2, seatPrice);
                }
            }
        });

        g3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (G3.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    g3.setBackgroundResource(R.drawable.red_chair);
                    G3 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!G3.equals("yes")) {
                    seat = seat + 1;
                    G3 = ForSelect(seat, g3, seatPrice);
                }
            }
        });

        g4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (G4.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    g4.setBackgroundResource(R.drawable.red_chair);
                    G4 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!G4.equals("yes")) {
                    seat = seat + 1;
                    G4 = ForSelect(seat, g4, seatPrice);
                }
            }
        });

        g5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (G5.equals("Select")) {
                    seat = seat - 1;
                    seatAndAmount.setText(seat + " x " + seatPrice + " = " + seatPrice * seat);
                    purchase.setText("Buy " + seat + " Seat " + seatPrice * seat + " Taka");
                    g5.setBackgroundResource(R.drawable.red_chair);
                    G5 = "";
                    ForPurchaseLayoutOpen(seat);
                } else if (!G5.equals("yes")) {
                    seat = seat + 1;
                    G5 = ForSelect(seat, g5, seatPrice);
                }
            }
        });



        purchaseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPurchase(A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,D1,D2,D3,D4,E1,E2,E3,E4,F1,F2,F3,F4,G1,G2,G3,G4,G5, ticketRef) == 0)
                {
                    seat = 0;
                    if (currentUser != null)
                    {
                        String currentUser2 = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        userRef = FirebaseDatabase.getInstance().getReference().child("Customer");
                        userRef.child(currentUser2).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                if (snapshot.exists())
                                {
                                    name.setText(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
                                    contact.setText(Objects.requireNonNull(snapshot.child("phone").getValue()).toString());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        purchaseLayout.setVisibility(View.GONE);
                        amountLayout.setVisibility(View.GONE);
                        ticketLayout.setVisibility(View.GONE);
                        downloadLayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        name.setVisibility(View.GONE);
                        contact.setVisibility(View.GONE);

                        passengerName.setVisibility(View.VISIBLE);
                        passengerContact.setVisibility(View.VISIBLE);

                        purchaseLayout.setVisibility(View.GONE);
                        amountLayout.setVisibility(View.GONE);
                        ticketLayout.setVisibility(View.GONE);
                        downloadLayout.setVisibility(View.VISIBLE);
                    }

                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadLayout.setVisibility(View.GONE);
                ticketLayout.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });


        ticketDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (currentUser != null)
                {
                    ticketDownloadBtn.setText("Thank You");


                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("PassengerName", name.getText().toString());
                    map.put("From", from.getText().toString());
                    map.put("To", to.getText().toString());
                    map.put("Date", date.getText().toString());
                    map.put("Time", time.getText().toString());
                    map.put("BusName", comapanyName);
                    map.put("Phone", contact.getText().toString());
                    map.put("Seat", finalSeatName);
                    map.put("Price", seatPrice);
                    map.put("CoachNo", coachNo);
                    push = from.getText().toString()+to.getText().toString()+finalSeatName;

                    userRef = FirebaseDatabase.getInstance().getReference().child("Customer");
                    userRef.child(currentUser.getUid()).child("MyTicket").child(push).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                downloadLayout.setVisibility(View.GONE);
                                ticketLayout.setVisibility(View.VISIBLE);
                                Toast.makeText(TicketActivity.this, "Your ticket save to your account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    userRef.child(currentUser.getUid()).child("MyTicketID").child(push).setValue(push);

                    convertXmlToPdf(push, currentUser.getUid());

                }
                else
                {
                    if (!passengerName.getText().toString().equals("") && !passengerContact.getText().toString().equals(""))
                    {
                        ticketDownloadBtn.setText("Thank You");
                        guestConvertXmlToPdf(passengerName, passengerContact, from, to, date, time, comapanyName, finalSeatName, seatPrice, coachNo);
                        downloadLayout.setVisibility(View.GONE);
                        ticketLayout.setVisibility(View.VISIBLE);
                    }
                }
                passengerName.setHint("Write Your Name");
                passengerContact.setHint("Write Your Phone Number");
                ticketDownloadBtn.setText("Download Ticket");
            }
        });




    }



    private void serverLoad(DatabaseReference ticketRef) {


        ticketRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    A1 = snapshot.child("a1").getValue().toString();
                    A2 = snapshot.child("a2").getValue().toString();
                    A3 = snapshot.child("a3").getValue().toString();
                    A4 = snapshot.child("a4").getValue().toString();
                    B1 = snapshot.child("b1").getValue().toString();
                    B2 = snapshot.child("b2").getValue().toString();
                    B3 = snapshot.child("b3").getValue().toString();
                    B4 = snapshot.child("b4").getValue().toString();
                    C1 = snapshot.child("c1").getValue().toString();
                    C2 = snapshot.child("c2").getValue().toString();
                    C3 = snapshot.child("c3").getValue().toString();
                    C4 = snapshot.child("c4").getValue().toString();
                    D1 = snapshot.child("d1").getValue().toString();
                    D2 = snapshot.child("d2").getValue().toString();
                    D3 = snapshot.child("d3").getValue().toString();
                    D4 = snapshot.child("d4").getValue().toString();
                    E1 = snapshot.child("e1").getValue().toString();
                    E2 = snapshot.child("e2").getValue().toString();
                    E3 = snapshot.child("e3").getValue().toString();
                    E4 = snapshot.child("e4").getValue().toString();
                    F1 = snapshot.child("f1").getValue().toString();
                    F2 = snapshot.child("f2").getValue().toString();
                    F3 = snapshot.child("f3").getValue().toString();
                    F4 = snapshot.child("f4").getValue().toString();
                    G1 = snapshot.child("g1").getValue().toString();
                    G2 = snapshot.child("g2").getValue().toString();
                    G3 = snapshot.child("g3").getValue().toString();
                    G4 = snapshot.child("g4").getValue().toString();

                    coachNo = snapshot.child("coachNo").getValue().toString();
                    String Price = snapshot.child("price").getValue().toString();
                    seatPrice = Integer.parseInt(Price);


                    if (A1.equals("yes"))
                    {
                        a1.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (A2.equals("yes"))
                    {
                        a2.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (A3.equals("yes"))
                    {
                        a3.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (A4.equals("yes"))
                    {
                        a4.setBackgroundResource(R.drawable.green_chair);
                    }

                    if (B1.equals("yes")) {
                        b1.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (B2.equals("yes")) {
                        b2.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (B3.equals("yes")) {
                        b3.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (B4.equals("yes")) {
                        b4.setBackgroundResource(R.drawable.green_chair);
                    }

                    if (C1.equals("yes")) {
                        c1.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (C2.equals("yes")) {
                        c2.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (C3.equals("yes")) {
                        c3.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (C4.equals("yes")) {
                        c4.setBackgroundResource(R.drawable.green_chair);
                    }

                    if (D1.equals("yes")) {
                        d1.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (D2.equals("yes")) {
                        d2.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (D3.equals("yes")) {
                        d3.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (D4.equals("yes")) {
                        d4.setBackgroundResource(R.drawable.green_chair);
                    }

                    if (E1.equals("yes")) {
                        e1.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (E2.equals("yes")) {
                        e2.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (E3.equals("yes")) {
                        e3.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (E4.equals("yes")) {
                        e4.setBackgroundResource(R.drawable.green_chair);
                    }

                    if (F1.equals("yes")) {
                        f1.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (F2.equals("yes")) {
                        f2.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (F3.equals("yes")) {
                        f3.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (F4.equals("yes")) {
                        f4.setBackgroundResource(R.drawable.green_chair);
                    }

                    if (G1.equals("yes")) {
                        g1.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (G2.equals("yes")) {
                        g2.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (G3.equals("yes")) {
                        g3.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (G4.equals("yes")) {
                        g4.setBackgroundResource(R.drawable.green_chair);
                    }
                    if (G5.equals("yes")) {
                        g5.setBackgroundResource(R.drawable.green_chair);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private Integer checkPurchase(String A1, String A2, String A3, String A4, String B1, String B2,String B3, String B4,String C1,String C2,String C3,String C4,String D1,String D2,String D3,String D4,String E1,String E2,String E3,String E4,String F1,String F2,String F3,String F4,String G1,String G2,String G3,String G4,String G5, DatabaseReference ticketRef) {
        String seat = "";
        if (A1.equals("Select"))
        {
            ticketRef.child("a1").setValue("yes");
            seat = "(A1)";
        }
        if (A2.equals("Select"))
        {
            ticketRef.child("a2").setValue("yes");
            seat += "(A2)";
        }
        if (A3.equals("Select"))
        {
            ticketRef.child("a3").setValue("yes");
            seat += "(A3)";
        }
        if (A4.equals("Select"))
        {
            ticketRef.child("a4").setValue("yes");
            seat += "(A4)";
        }

        if (B1.equals("Select"))
        {
            ticketRef.child("b1").setValue("yes");
            seat = "(B1)";
        }
        if (B2.equals("Select"))
        {
            ticketRef.child("b2").setValue("yes");
            seat += "(B2)";
        }
        if (B3.equals("Select"))
        {
            ticketRef.child("b3").setValue("yes");
            seat += "(B3)";
        }
        if (B4.equals("Select"))
        {
            ticketRef.child("b4").setValue("yes");
            seat += "(B4)";
        }

        if (C1.equals("Select")) {
            ticketRef.child("c1").setValue("yes");
            seat = "(C1)";
        }
        if (C2.equals("Select")) {
            ticketRef.child("c2").setValue("yes");
            seat += "(C2)";
        }
        if (C3.equals("Select")) {
            ticketRef.child("c3").setValue("yes");
            seat += "(C3)";
        }
        if (C4.equals("Select")) {
            ticketRef.child("c4").setValue("yes");
            seat += "(C4)";
        }

        if (D1.equals("Select")) {
            ticketRef.child("d1").setValue("yes");
            seat = "(D1)";
        }
        if (D2.equals("Select")) {
            ticketRef.child("d2").setValue("yes");
            seat += "(D2)";
        }
        if (D3.equals("Select")) {
            ticketRef.child("d3").setValue("yes");
            seat += "(D3)";
        }
        if (D4.equals("Select")) {
            ticketRef.child("d4").setValue("yes");
            seat += "(D4)";
        }

        if (E1.equals("Select")) {
            ticketRef.child("e1").setValue("yes");
            seat = "(E1)";
        }
        if (E2.equals("Select")) {
            ticketRef.child("e2").setValue("yes");
            seat += "(E2)";
        }
        if (E3.equals("Select")) {
            ticketRef.child("e3").setValue("yes");
            seat += "(E3)";
        }
        if (E4.equals("Select")) {
            ticketRef.child("e4").setValue("yes");
            seat += "(E4)";
        }

        if (F1.equals("Select")) {
            ticketRef.child("f1").setValue("yes");
            seat = "(F1)";
        }
        if (F2.equals("Select")) {
            ticketRef.child("f2").setValue("yes");
            seat += "(F2)";
        }
        if (F3.equals("Select")) {
            ticketRef.child("f3").setValue("yes");
            seat += "(F3)";
        }
        if (F4.equals("Select")) {
            ticketRef.child("f4").setValue("yes");
            seat += "(F4)";
        }


        if (G1.equals("Select")) {
            ticketRef.child("g1").setValue("yes");
            seat = "(G1)";
        }
        if (G2.equals("Select")) {
            ticketRef.child("g2").setValue("yes");
            seat += "(G2)";
        }
        if (G3.equals("Select")) {
            ticketRef.child("g3").setValue("yes");
            seat += "(G3)";
        }
        if (G4.equals("Select")) {
            ticketRef.child("g4").setValue("yes");
            seat += "(G4)";
        }
        if (G5.equals("Select")) {
            ticketRef.child("g5").setValue("yes");
            seat += "(G5)";
        }



        serverLoad(ticketRef);

        finalSeatName = seat;
        ticketRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    from.setText(snapshot.child("from").getValue().toString());
                    to.setText(snapshot.child("to").getValue().toString());
                    date.setText(snapshot.child("date").getValue().toString());
                    time.setText(snapshot.child("time").getValue().toString());
                    seatName.setText(finalSeatName);
                    price.setText(seatAndAmount.getText().toString());
                    contact.setText(snapshot.child("phone").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return 0;

    }

    public String ForSelect(int seat, LinearLayout a, int seatPrice) {
        seatAndAmount.setText(seat+" x "+ seatPrice +" = "+ seatPrice*seat);
        purchase.setText("Buy "+ seat+ " Seat " + seatPrice*seat + " Taka");
        a.setBackgroundResource(R.drawable.select_chair);
        ForPurchaseLayoutOpen(seat);

        return "Select";
    }


    private void ForPurchaseLayoutOpen(int seat) {
        if (seat ==0)
        {
            purchaseLayout.setVisibility(View.GONE);
            amountLayout.setVisibility(View.GONE);
        }
        else
        {
            purchaseLayout.setVisibility(View.VISIBLE);
            amountLayout.setVisibility(View.VISIBLE);
        }
    }


    public void convertXmlToPdf(String push, String uid) {
        // Inflate the XML layout file
        TextView From1, To1, Date1, Time1, Price1, Seat1, Name1, Phone1,Coach1, BusName1, From2, To2, Date2, Time2, Price2, Seat2, Name2, Phone2,Coach2, From3, To3, Date3, Time3, Price3, Seat3, Name3, Phone3,Coach3;
        View view = LayoutInflater.from(this).inflate(R.layout.activity_pdfgenerator, null);
        From1 = view.findViewById(R.id.from_1);
        To1 = view.findViewById(R.id.to_1);
        Date1 = view.findViewById(R.id.date_1);
        Time1 = view.findViewById(R.id.time_1);
        Price1 = view.findViewById(R.id.price_1);
        Seat1 = view.findViewById(R.id.seat_1);
        Name1 = view.findViewById(R.id.name_1);
        Phone1 = view.findViewById(R.id.mobile_1);
        Coach1 = view.findViewById(R.id.coach_1);
        BusName1 = view.findViewById(R.id.bus_name_for_ticket);

        From2 = view.findViewById(R.id.from_2);
        To2 = view.findViewById(R.id.to_2);
        Date2 = view.findViewById(R.id.date_2);
        Time2 = view.findViewById(R.id.time_2);
        Price2 = view.findViewById(R.id.price_2);
        Seat2 = view.findViewById(R.id.seat_2);
        Name2 = view.findViewById(R.id.name_2);
        Phone2 = view.findViewById(R.id.mobile_2);
        Coach2 = view.findViewById(R.id.coach_2);

        From3 = view.findViewById(R.id.from_3);
        To3 = view.findViewById(R.id.to_3);
        Date3 = view.findViewById(R.id.date_3);
        Time3 = view.findViewById(R.id.time_3);
        Price3 = view.findViewById(R.id.price_3);
        Seat3 = view.findViewById(R.id.seat_3);
        Name3 = view.findViewById(R.id.name_3);
        Phone3 = view.findViewById(R.id.mobile_3);
        Coach3 = view.findViewById(R.id.coach_3);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");

        databaseReference.child(uid).child("MyTicket").child(push).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if (snapshot.exists())
                {
                    BusName1.setText(Objects.requireNonNull(snapshot.child("BusName").getValue()).toString());
                    From1.setText(Objects.requireNonNull(snapshot.child("From").getValue()).toString());
                    To1.setText(Objects.requireNonNull(snapshot.child("To").getValue()).toString());
                    Date1.setText(Objects.requireNonNull(snapshot.child("Date").getValue()).toString());
                    Time1.setText(Objects.requireNonNull(snapshot.child("Time").getValue()).toString());
                    Seat1.setText(Objects.requireNonNull(snapshot.child("Seat").getValue()).toString());
                    Price1.setText(Objects.requireNonNull(snapshot.child("Price").getValue()).toString());
                    Phone1.setText(Objects.requireNonNull(snapshot.child("Phone").getValue()).toString());
                    Coach1.setText(Objects.requireNonNull(snapshot.child("CoachNo").getValue()).toString());
                    Name1.setText(Objects.requireNonNull(snapshot.child("PassengerName").getValue()).toString());


                    From2.setText(Objects.requireNonNull(snapshot.child("From").getValue()).toString());
                    To2.setText(Objects.requireNonNull(snapshot.child("To").getValue()).toString());
                    Date2.setText(Objects.requireNonNull(snapshot.child("Date").getValue()).toString());
                    Time2.setText(Objects.requireNonNull(snapshot.child("Time").getValue()).toString());
                    Seat2.setText(Objects.requireNonNull(snapshot.child("Seat").getValue()).toString());
                    Price2.setText(Objects.requireNonNull(snapshot.child("Price").getValue()).toString());
                    Phone2.setText(Objects.requireNonNull(snapshot.child("Phone").getValue()).toString());
                    Coach2.setText(Objects.requireNonNull(snapshot.child("CoachNo").getValue()).toString());
                    Name2.setText(Objects.requireNonNull(snapshot.child("PassengerName").getValue()).toString());

                    From3.setText(Objects.requireNonNull(snapshot.child("From").getValue()).toString());
                    To3.setText(Objects.requireNonNull(snapshot.child("To").getValue()).toString());
                    Date3.setText(Objects.requireNonNull(snapshot.child("Date").getValue()).toString());
                    Time3.setText(Objects.requireNonNull(snapshot.child("Time").getValue()).toString());
                    Seat3.setText(Objects.requireNonNull(snapshot.child("Seat").getValue()).toString());
                    Price3.setText(Objects.requireNonNull(snapshot.child("Price").getValue()).toString());
                    Phone3.setText(Objects.requireNonNull(snapshot.child("Phone").getValue()).toString());
                    Coach3.setText(Objects.requireNonNull(snapshot.child("CoachNo").getValue()).toString());
                    Name3.setText(Objects.requireNonNull(snapshot.child("PassengerName").getValue()).toString());

                    DisplayMetrics displayMetrics = new DisplayMetrics();


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Objects.requireNonNull(TicketActivity.this.getDisplay()).getRealMetrics(displayMetrics);
                    } else
                        TicketActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                    view.measure(View.MeasureSpec.makeMeasureSpec(2480, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(3508, View.MeasureSpec.EXACTLY));
                    Log.d("mylog", "Width Now " + view.getMeasuredWidth());
                    view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                    // Create a new PdfDocument instance
                    PdfDocument document = new PdfDocument();

                    // Obtain the width and height of the view
                    int viewWidth = view.getMeasuredWidth();
                    int viewHeight = view.getMeasuredHeight();
                    // Create a PageInfo object specifying the page attributes
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create();

                    // Start a new page
                    PdfDocument.Page page = document.startPage(pageInfo);

                    // Get the Canvas object to draw on the page
                    Canvas canvas = page.getCanvas();

                    // Create a Paint object for styling the view
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);

                    // Draw the view on the canvas
                    view.draw(canvas);

                    // Finish the page
                    document.finishPage(page);

                    // Specify the path and filename of the output PDF file
                    File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    // Get the current time
                    Date currentTime = new Date();

                    // Format the time using SimpleDateFormat
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String formattedTime = sdf.format(currentTime);


                    String fileName = "Ticket("+push+").pdf";
                    File filePath = new File(downloadsDir, fileName);


                    try {
                        FileOutputStream fos = new FileOutputStream(filePath);
                        document.writeTo(fos);
                        document.close();
                        fos.close();
                        // PDF conversion successful
                        Toast.makeText(TicketActivity.this, "Download Successful", Toast.LENGTH_LONG).show();
                        fileName = "";


                    } catch (IOException e) {
                        e.printStackTrace();
                        // Error occurred while converting to PDF
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    public void guestConvertXmlToPdf(EditText passengerName, EditText passengerContact, TextView from, TextView to, TextView date, TextView time, String comapanyName, String finalSeatName, int seatPrice, String coachNo) {
        // Inflate the XML layout file
        TextView From1, To1, Date1, Time1, Price1, Seat1, Name1, Phone1,Coach1, BusName1, From2, To2, Date2, Time2, Price2, Seat2, Name2, Phone2,Coach2, From3, To3, Date3, Time3, Price3, Seat3, Name3, Phone3,Coach3;
        View view = LayoutInflater.from(this).inflate(R.layout.activity_pdfgenerator, null);
        From1 = view.findViewById(R.id.from_1);
        To1 = view.findViewById(R.id.to_1);
        Date1 = view.findViewById(R.id.date_1);
        Time1 = view.findViewById(R.id.time_1);
        Price1 = view.findViewById(R.id.price_1);
        Seat1 = view.findViewById(R.id.seat_1);
        Name1 = view.findViewById(R.id.name_1);
        Phone1 = view.findViewById(R.id.mobile_1);
        Coach1 = view.findViewById(R.id.coach_1);
        BusName1 = view.findViewById(R.id.bus_name_for_ticket);

        From2 = view.findViewById(R.id.from_2);
        To2 = view.findViewById(R.id.to_2);
        Date2 = view.findViewById(R.id.date_2);
        Time2 = view.findViewById(R.id.time_2);
        Price2 = view.findViewById(R.id.price_2);
        Seat2 = view.findViewById(R.id.seat_2);
        Name2 = view.findViewById(R.id.name_2);
        Phone2 = view.findViewById(R.id.mobile_2);
        Coach2 = view.findViewById(R.id.coach_2);

        From3 = view.findViewById(R.id.from_3);
        To3 = view.findViewById(R.id.to_3);
        Date3 = view.findViewById(R.id.date_3);
        Time3 = view.findViewById(R.id.time_3);
        Price3 = view.findViewById(R.id.price_3);
        Seat3 = view.findViewById(R.id.seat_3);
        Name3 = view.findViewById(R.id.name_3);
        Phone3 = view.findViewById(R.id.mobile_3);
        Coach3 = view.findViewById(R.id.coach_3);


        BusName1.setText(comapanyName);
        From1.setText(from.getText().toString());
        To1.setText(to.getText().toString());
        Date1.setText(date.getText().toString());
        Time1.setText(time.getText().toString());
        Seat1.setText(finalSeatName);
        Price1.setText(String.valueOf(seatPrice));
        Phone1.setText(passengerContact.getText().toString());
        Coach1.setText(coachNo);
        Name1.setText(passengerName.getText().toString());


        From2.setText(from.getText().toString());
        To2.setText(to.getText().toString());
        Date2.setText(date.getText().toString());
        Time2.setText(time.getText().toString());
        Seat2.setText(finalSeatName);
        Price2.setText(String.valueOf(seatPrice));
        Phone2.setText(passengerContact.getText().toString());
        Coach2.setText(coachNo);
        Name2.setText(passengerName.getText().toString());

        From3.setText(from.getText().toString());
        To3.setText(to.getText().toString());
        Date3.setText(date.getText().toString());
        Time3.setText(time.getText().toString());
        Seat3.setText(finalSeatName);
        Price3.setText(String.valueOf(seatPrice));
        Phone3.setText(passengerContact.getText().toString());
        Coach3.setText(coachNo);
        Name3.setText(passengerName.getText().toString());




        DisplayMetrics displayMetrics = new DisplayMetrics();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getDisplay().getRealMetrics(displayMetrics);
        } else
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        view.measure(View.MeasureSpec.makeMeasureSpec(2480, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(3508, View.MeasureSpec.EXACTLY));
        Log.d("mylog", "Width Now " + view.getMeasuredWidth());
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        // Create a new PdfDocument instance
        PdfDocument document = new PdfDocument();

        // Obtain the width and height of the view
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();
        // Create a PageInfo object specifying the page attributes
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create();

        // Start a new page
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the Canvas object to draw on the page
        Canvas canvas = page.getCanvas();

        // Create a Paint object for styling the view
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // Draw the view on the canvas
        view.draw(canvas);

        // Finish the page
        document.finishPage(page);

        // Specify the path and filename of the output PDF file
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // Get the current time
        Date currentTime = new Date();

        // Format the time using SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = sdf.format(currentTime);


        String fileName = "Ticket("+From1.getText().toString()+To1.getText().toString()+finalSeatName+").pdf";
        File filePath = new File(downloadsDir, fileName);


        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            document.writeTo(fos);
            document.close();
            fos.close();
            // PDF conversion successful
            Toast.makeText(this, "Download Successful", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while converting to PDF
        }


    }

}