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
        TextView fromL,toL,dateL,timeL,contactL,seatNameL,priceL;
        View view = LayoutInflater.from(this).inflate(R.layout.activity_pdfgenerator, null);
        fromL = view.findViewById(R.id.from);
        toL = view.findViewById(R.id.to);
        dateL = view.findViewById(R.id.date);
        timeL = view.findViewById(R.id.time);
        contactL = view.findViewById(R.id.contact);
        seatNameL = view.findViewById(R.id.seat);
        priceL = view.findViewById(R.id.price);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");

        databaseReference.child(currentUser).child("MyTicket").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                /**
                 map.put("name", name.toString());
                 map.put("from", from.toString());
                 map.put("to", to.toString());
                 map.put("date", date.toString());
                 map.put("time", time.toString());
                 map.put("busName", comapanyName);
                 map.put("phone", contact.toString());
                 map.put("seat", finalSeatName);
                 map.put("price", seatPrice);
                 */
                if (snapshot.exists())
                {
                    fromL.setText(Objects.requireNonNull(snapshot.child("From").getValue()).toString());
                    toL.setText(Objects.requireNonNull(snapshot.child("To").getValue()).toString());
                    dateL.setText(Objects.requireNonNull(snapshot.child("Date").getValue()).toString());
                    timeL.setText(Objects.requireNonNull(snapshot.child("Time").getValue()).toString());
                    seatNameL.setText(Objects.requireNonNull(snapshot.child("Seat").getValue()).toString());
                    priceL.setText(Objects.requireNonNull(snapshot.child("Price").getValue()).toString());
                    contactL.setText(Objects.requireNonNull(snapshot.child("Phone").getValue()).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        DisplayMetrics displayMetrics = new DisplayMetrics();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getDisplay().getRealMetrics(displayMetrics);
        } else
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));
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


        String fileName = "Ticket.pdf";
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