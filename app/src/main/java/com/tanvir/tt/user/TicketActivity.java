package com.tanvir.tt.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    LinearLayout a1,a2,a3,a4;
    RelativeLayout amountLayout, purchaseLayout;
    TextView seatAndAmount, purchase;
    static String A1,A2,A3,A4 = "";
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
        purchase = findViewById(R.id.purchase);
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



        serverLoad(ticketRef);

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

        purchaseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPurchase(A1,A2,A3,A4, ticketRef) == 0)
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





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private Integer checkPurchase(String A1, String A2, String A3, String A4, DatabaseReference ticketRef) {
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