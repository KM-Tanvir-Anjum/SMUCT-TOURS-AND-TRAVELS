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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MyTicketActivity extends AppCompatActivity {

    ImageView backBtn;
    Spinner myTicket;
    Button download;

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);

        mAuth = FirebaseAuth.getInstance();
        currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");


        myTicket = findViewById(R.id.my_ticket_spinner);
        download = findViewById(R.id.my_ticket_download);
        backBtn = findViewById(R.id.myticket_ticket_backBtn);




        try {

            databaseReference.child(currentUser).child("MyTicketID").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<String> data = new ArrayList<>();
                    data.add("Select Your Ticket");

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String item = snapshot.getValue(String.class);
                        data.add(item);
                    }


                    // Create an ArrayAdapter using the data list
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MyTicketActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    myTicket.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        } catch (Exception e) {

        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                convertXmlToPdf(myTicket.getSelectedItem().toString());

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }


    public void convertXmlToPdf(String id) {
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


        mAuth = FirebaseAuth.getInstance();
        currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");

        databaseReference.child(currentUser).child("MyTicket").child(id).addValueEventListener(new ValueEventListener() {
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
                        Objects.requireNonNull(MyTicketActivity.this.getDisplay()).getRealMetrics(displayMetrics);
                    } else
                        MyTicketActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

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


                    String fileName = "Ticket("+id+").pdf";
                    File filePath = new File(downloadsDir, fileName);


                    try {
                        FileOutputStream fos = new FileOutputStream(filePath);
                        document.writeTo(fos);
                        document.close();
                        fos.close();
                        // PDF conversion successful
                        Toast.makeText(MyTicketActivity.this, "Download Successful", Toast.LENGTH_LONG).show();



                    } catch (IOException e) {
                        e.printStackTrace();
                        // Error occurred while converting to PDF
                    }

                    fileName = "";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}