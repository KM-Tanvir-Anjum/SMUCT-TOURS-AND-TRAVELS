package com.tanvir.tt.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanvir.tt.R;
import com.tanvir.tt.activity.MainActivity;
import com.tanvir.tt.activity_faq;
import com.tanvir.tt.user.HomeCategoryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import next.firenext.NextDatabase;

public class AdminHomeActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    LinearLayout  ownerHomeLayout, newTicketUploadLayout, deleteTicketlayout;
    Button  uploadBtn;
    EditText  ticketPrice, coachNo;
    TextView uploadNewTicketBtn, DeleteTicketBtn, logOutBtn, companyProfile,termsAndConditions;
    ImageView newTicketUploadLayoutBackBtn;
    Spinner newFrom, newTo, newDay, newMonth, newYear, newHour, newMinute, newAmPm;


    Spinner deleteFrom, deleteTo, deleteDate, deleteTime;
    Button deleteBtn;
    ImageView deleteLayoutBackBtn;
    static String deleteCondition= "yes";
    String companyName, email, phoneNumber, currentUser;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        mAuth = FirebaseAuth.getInstance();
        currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("All Company").child("Company");

        ownerHomeLayout = findViewById(R.id.owner_home_layout);
        newTicketUploadLayout = findViewById(R.id.new_ticket_upload_Layout);
        uploadNewTicketBtn = findViewById(R.id.upload_new_ticketBtn);
        newFrom = findViewById(R.id.from_new_ticket);
        newTo = findViewById(R.id.to_new_ticket);
        newDay = findViewById(R.id.day_new_ticket);
        newMonth = findViewById(R.id.month_new_ticket);
        newYear = findViewById(R.id.year_new_ticket);
        newHour = findViewById(R.id.hour_new_ticket);
        newMinute = findViewById(R.id.minute_new_ticket);
        newAmPm = findViewById(R.id.ampm_new_ticket);
        uploadBtn = findViewById(R.id.upload_btn);
        newTicketUploadLayoutBackBtn = findViewById(R.id.new_ticket_layout_back_btn);
        ticketPrice = findViewById(R.id.new_ticket_price);
        coachNo = findViewById(R.id.coach_no);

        deleteFrom = findViewById(R.id.delete_from_spinner);
        deleteTo = findViewById(R.id.delete_to_spinner);
        deleteDate = findViewById(R.id.delete_schedule_date);
        deleteTime = findViewById(R.id.delete_schedule_time);
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteLayoutBackBtn = findViewById(R.id.delete_ticket_layout_back_btn);
        deleteTicketlayout = findViewById(R.id.deleteTicket_Layout);
        DeleteTicketBtn = findViewById(R.id.delete_ticketBtn);
        logOutBtn = findViewById(R.id.logoutBtn);

        companyProfile = findViewById(R.id.company_profile);
        termsAndConditions=findViewById(R.id.admin_terms_and_conditions);

        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, activity_admin_terms_and_condition.class);
                startActivity(intent);
            }
        });


        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    companyName = Objects.requireNonNull(snapshot.child("company").getValue()).toString();
                    email = Objects.requireNonNull(snapshot.child("email").getValue()).toString();
                    phoneNumber = Objects.requireNonNull(snapshot.child("phone").getValue()).toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        uploadNewTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTicketUploadLayout.setVisibility(View.VISIBLE);
                deleteTicketlayout.setVisibility(View.GONE);
                ownerHomeLayout.setVisibility(View.GONE);

            }
        });
        newTicketUploadLayoutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTicketUploadLayout.setVisibility(View.GONE);
                ownerHomeLayout.setVisibility(View.VISIBLE);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newFrom.getSelectedItem().toString()!= "Select" && newTo.getSelectedItem().toString() != "Select"
                        && newDay.getSelectedItem().toString()!="dd" && newMonth.getSelectedItem().toString()!= "mm"
                        && newYear.getSelectedItem().toString()!= "yy" && newHour.getSelectedItem().toString()!="HH"
                        && newMinute.getSelectedItem().toString()!="MM" && ticketPrice.getText().toString()!="" && coachNo.getText().toString()!="")
                {
                    uploadData(newFrom, newTo, newDay, newMonth, newYear, newHour, newMinute, newAmPm, ticketPrice, coachNo);

                }
            }
        });


        //---------------------- for delete ----------------------------------------

        DeleteTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTicketUploadLayout.setVisibility(View.GONE);
                deleteTicketlayout.setVisibility(View.VISIBLE);
                ownerHomeLayout.setVisibility(View.GONE);
            }
        });

        deleteLayoutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTicketlayout.setVisibility(View.GONE);
                ownerHomeLayout.setVisibility(View.VISIBLE);
            }
        });

        //------------ delete data ------------//
        deleteFromSpinnerData(deleteFrom);

        deleteFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(HomeActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                deleteToSpinnerData(deleteTo, selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        deleteTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(HomeActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                deleteDateSpinnerData(deleteDate, deleteFrom.getSelectedItem().toString() + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        deleteDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(HomeActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                deleteTimeSpinnerData(deleteTime, deleteFrom.getSelectedItem().toString() + deleteTo.getSelectedItem().toString() + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteFrom.getSelectedItem().toString() != "Select" && deleteTo.getSelectedItem().toString() != "Select"
                        && deleteDate.getSelectedItem().toString() != "Select" && deleteTime.getSelectedItem().toString() != "Select") {
                    deleteDataFromServer(deleteFrom, deleteTo, deleteDate, deleteTime);
                    deleteFrom.setSelection(0);
                }
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                NextDatabase.delete(getApplicationContext(),"user:");
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        companyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, CompanyProfile.class);
                startActivity(intent);
            }
        });



    }



    private void uploadData(Spinner newFrom, Spinner newTo, Spinner newDay, Spinner newMonth, Spinner newYear, Spinner newHour, Spinner newMinute, Spinner newAmPm, EditText ticketPrice, EditText coachNo) {

        DatabaseReference scheduleRef = database.getReference("Bus Schedule");
        scheduleRef.child("From").child(newFrom.getSelectedItem().toString()).setValue(newFrom.getSelectedItem().toString());

        scheduleRef.child(newFrom.getSelectedItem().toString()).child(newTo.getSelectedItem().toString()).setValue(newTo.getSelectedItem().toString());

        String date = newDay.getSelectedItem().toString()+"-"+newMonth.getSelectedItem().toString()+"-"+newYear.getSelectedItem().toString();
        scheduleRef.child(newFrom.getSelectedItem().toString()+newTo.getSelectedItem().toString()).child(date).setValue(date);

        String time = newHour.getSelectedItem().toString()+":"+newMinute.getSelectedItem().toString()+newAmPm.getSelectedItem().toString();
        scheduleRef.child(newFrom.getSelectedItem().toString()+newTo.getSelectedItem().toString()+date).child(time).setValue(time);

        scheduleRef.child(newFrom.getSelectedItem().toString()+newTo.getSelectedItem().toString()+date+time).child(companyName).setValue(companyName);

        scheduleRef.child(newFrom.getSelectedItem().toString() + newTo.getSelectedItem().toString() + date + time)
                .child("companyName").setValue(companyName);

        DatabaseReference ticketRef = database.getReference("Bus Ticket");

        HashMap ticket = new HashMap();
        ticket.put("a1", "no");
        ticket.put("a2", "no");
        ticket.put("a3", "no");
        ticket.put("a4", "no");
        ticket.put("b1", "no");
        ticket.put("b2", "no");
        ticket.put("b3", "no");
        ticket.put("b4", "no");
        ticket.put("c1", "no");
        ticket.put("c2", "no");
        ticket.put("c3", "no");
        ticket.put("c4", "no");
        ticket.put("d1", "no");
        ticket.put("d2", "no");
        ticket.put("d3", "no");
        ticket.put("d4", "no");
        ticket.put("e1", "no");
        ticket.put("e2", "no");
        ticket.put("e3", "no");
        ticket.put("e4", "no");
        ticket.put("f1", "no");
        ticket.put("f2", "no");
        ticket.put("f3", "no");
        ticket.put("f4", "no");
        ticket.put("g1", "no");
        ticket.put("g2", "no");
        ticket.put("g3", "no");
        ticket.put("g4", "no");
        ticket.put("company", companyName);
        ticket.put("phone", phoneNumber);
        ticket.put("email", email);
        ticket.put("price",ticketPrice.getText().toString());
        ticket.put("coachNo",coachNo.getText().toString());
        ticket.put("from", newFrom.getSelectedItem().toString());
        ticket.put("to", newTo.getSelectedItem().toString());
        ticket.put("date", newDay.getSelectedItem().toString()+"-"+newMonth.getSelectedItem().toString()+"-"+newYear.getSelectedItem().toString());
        ticket.put("time", newHour.getSelectedItem().toString()+":"+newMinute.getSelectedItem().toString()+newAmPm.getSelectedItem().toString());


        ticketRef.child(newFrom.getSelectedItem().toString()+newTo.getSelectedItem().toString()).child(date+time).child(companyName.toString()).updateChildren(ticket).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if (task.isSuccessful())
                {
                    ticketPrice.setText("");
                    newHour.setSelection(0);
                    newMinute.setSelection(0);
                }
            }
        });
    }

    private void fromSpinnerData(Spinner fromSpinner) {
        DatabaseReference dateRef = database.getReference("Bus Schedule").child("From");
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
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


    //------------------------ for delete ------------------------------------------

    private  void deleteFromSpinnerData(Spinner fromSpinner) {
        DatabaseReference dateRef = database.getReference("Bus Schedule").child("From");
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Set the adapter to the Spinner
                deleteFrom.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }
    private void deleteToSpinnerData(Spinner toSpinner, String selectedItem) {
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    deleteTo.setAdapter(adapter);

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

    private void deleteDateSpinnerData(Spinner dateSpinner, String fromTo) {
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    deleteDate.setAdapter(adapter);
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

    private void deleteTimeSpinnerData(Spinner timeSpinner, String fromToDate) {
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHomeActivity.this, android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    deleteTime.setAdapter(adapter);

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

    private void deleteDataFromServer(Spinner deleteFrom, Spinner deleteTo, Spinner deleteDate, Spinner deleteTime) {

        DatabaseReference deleteTicketRef = database.getReference("Bus Ticket");

        deleteTicketRef.child(deleteFrom.getSelectedItem().toString()+deleteTo.getSelectedItem().toString()).child(deleteDate.getSelectedItem().toString()+deleteTime.getSelectedItem().toString()+companyName).removeValue();

        DatabaseReference deleteScheduleRef = database.getReference("Bus Schedule");

        //--- delete schedule time ---//
        deleteScheduleRef.child(deleteFrom.getSelectedItem().toString()+deleteTo.getSelectedItem().toString()+deleteDate.getSelectedItem().toString()).child(deleteTime.getSelectedItem().toString()+companyName).removeValue();


    }





}