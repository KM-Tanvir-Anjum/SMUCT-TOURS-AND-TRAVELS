package com.tanvir.tt.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tanvir.tt.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeclectionTicketActivity extends AppCompatActivity {

    LinearLayout userLayout, guestLayout;
    TextView forLogIn;
    ImageView notice;
    private String guest = "false";

    DatabaseReference noticeReff;


    // -------------- For User --------------- //
    Spinner dateSpinner, timeSpinner, fromSpinner, toSpinner, companyNameSpinner;
    Button nextBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_ticket);


        guest = getIntent().getStringExtra("guest");


        notice = findViewById(R.id.notice_board);


        guestLayout = findViewById(R.id.guest_layout);
        forLogIn = findViewById(R.id.home_log_in_your_account);
        forLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeclectionTicketActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (guest.equals("false"))
        {
            guestLayout.setVisibility(View.GONE);
        }
        else
        {
            guestLayout.setVisibility(View.VISIBLE);
        }




        // --------------- for spinner ----------------//
        dateSpinner = findViewById(R.id.schedule_date);
        timeSpinner = findViewById(R.id.schedule_time);
        fromSpinner = findViewById(R.id.from_spinner);
        toSpinner = findViewById(R.id.to_spinner);
        companyNameSpinner = findViewById(R.id.company_name);
        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.selection_ticket_backBtn);

        fromSpinnerData(fromSpinner);


        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(HomeActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                toSpinnerData(toSpinner, selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(HomeActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                dateSpinnerData(dateSpinner, fromSpinner.getSelectedItem().toString() + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(HomeActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                timeSpinnerData(timeSpinner, fromSpinner.getSelectedItem().toString() + toSpinner.getSelectedItem().toString() + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(HomeActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                CompanySpinnerData(companyNameSpinner, fromSpinner.getSelectedItem().toString() + toSpinner.getSelectedItem().toString()+ dateSpinner.getSelectedItem().toString()+selectedItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (fromSpinner.getSelectedItem().toString() != "Select" && toSpinner.getSelectedItem().toString() != "Select"
                        && dateSpinner.getSelectedItem().toString() != "Select" && timeSpinner.getSelectedItem().toString() != "Select"
                       && companyNameSpinner.getSelectedItem().toString() != "Select") {

                   Intent intent = new Intent(SeclectionTicketActivity.this, TicketActivity.class);
                   intent.putExtra("fromTo", fromSpinner.getSelectedItem().toString() + toSpinner.getSelectedItem().toString());
                   intent.putExtra("DateTime", dateSpinner.getSelectedItem().toString() + timeSpinner.getSelectedItem().toString());
                   intent.putExtra("companyName", companyNameSpinner.getSelectedItem().toString());
                   startActivity(intent);

                }


            }
        });

        noticeReff = FirebaseDatabase.getInstance().getReference().child("Notice");

        noticeReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    String img = Objects.requireNonNull(snapshot.child("url").getValue()).toString();
                    Picasso.get()
                            .load(img.toString())
                            .into(notice);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    private void fromSpinnerData(Spinner fromSpinner) {
        DatabaseReference dateRef = database.getReference("Bus Schedule").child("From");
        dateRef.addValueEventListener(new ValueEventListener() {
            @Override
            //Inside the onDataChange method, a loop iterates through the children of the snapshot,
            // extracting String values from each child. These values represent items in the "From" node of the database
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> data = new ArrayList<>();
                data.add("Select");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String item = snapshot.getValue(String.class);
                    data.add(item);
                }

                // Create an ArrayAdapter using the data list
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SeclectionTicketActivity.this, android.R.layout.simple_spinner_item, data);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Set the adapter to the Spinner
                fromSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }


    private void toSpinnerData(Spinner toSpinner, String selectedItem) {
        try {
            DatabaseReference timeRef = database.getReference("Bus Schedule").child(selectedItem);
            timeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> data = new ArrayList<>();
                    data.add("Select");

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String item = snapshot.getValue(String.class);
                        data.add(item);
                    }


                    // Create an ArrayAdapter using the data list
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SeclectionTicketActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    toSpinner.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "From Destination Not Select", Toast.LENGTH_SHORT).show();
        }


    }

    private void dateSpinnerData(Spinner dateSpinner, String fromTo) {
        try {
            DatabaseReference dateRef = database.getReference("Bus Schedule").child(fromTo);
            dateRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> data = new ArrayList<>();
                    data.add("Select");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String item = snapshot.getValue(String.class);
                        data.add(item);
                    }

                    // Create an ArrayAdapter using the data list
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SeclectionTicketActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    dateSpinner.setAdapter(adapter);
                    //timeSpinnerData(timeSpinner, data.get(0));
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "To Destination Not Select", Toast.LENGTH_SHORT).show();
        }
    }

    private void timeSpinnerData(Spinner timeSpinner, String fromToDate) {
        try {
            DatabaseReference timeRef = database.getReference("Bus Schedule").child(fromToDate);
            timeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> data = new ArrayList<>();
                    data.add("Select");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String item = snapshot.getValue(String.class);
                        data.add(item);
                    }

                    // Create an ArrayAdapter using the data list
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SeclectionTicketActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    timeSpinner.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "'To' or 'Date' not select", Toast.LENGTH_SHORT).show();
        }


    }

    private void CompanySpinnerData(Spinner companyNameSpinner, String fromToDate) {
        try {
            DatabaseReference timeRef = database.getReference("Bus Schedule").child(fromToDate);
            timeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> data = new ArrayList<>();
                    data.add("Select");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String item = snapshot.getValue(String.class);
                        data.add(item);
                    }

                    // Create an ArrayAdapter using the data list
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SeclectionTicketActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    companyNameSpinner.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "'To' or 'Date' not select", Toast.LENGTH_SHORT).show();
        }
    }


}