package com.tanvir.tt.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tanvir.tt.Feedback;
import com.tanvir.tt.R;

public class FeedbackActivity extends AppCompatActivity {


    //FirebaseDatabase database=FirebaseDatabase.getInstance();
    ImageView backBtn;

    Button feedbackBtn;

    FirebaseAuth mAuth;
    DatabaseReference userReference;

    Spinner totalSeatSpinner,seat1Spinner,seat2Spinner,seat3Spinner,routeSpinner,seasonSpinner,dayNightSpinner,ratingsSpinner;

//    private Spinner totalSeatSpinner;
//    private Spinner seat1Spinner;
//    private Spinner seat2Spinner;
//    private Spinner seat3Spinner;

//    private LinearLayout seat1Layout;
//    private LinearLayout seat2Layout;
//    private LinearLayout seat3Layout;

    //private Button feedbackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mAuth = FirebaseAuth.getInstance();

        routeSpinner = findViewById(R.id.feedback_route_spinner);
        totalSeatSpinner = findViewById(R.id.total_seat_spinner);
        seat1Spinner = findViewById(R.id.feedback_seat1_spinner);
        seat2Spinner = findViewById(R.id.feedback_seat2_spinner);
        seat3Spinner = findViewById(R.id.feedback_seat3_spinner);
        seasonSpinner = findViewById(R.id.feedback_season_spinner);
        dayNightSpinner = findViewById(R.id.feedback_daynight_spinner);
        ratingsSpinner = findViewById(R.id.feedback_rateings_spinner);

        feedbackBtn = findViewById(R.id.feedback_button_spinner); // Add this line

        userReference = FirebaseDatabase.getInstance().getReference().child("Feedback");

        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFeedback();
            }
        });
    }
    private void updateFeedback(){
        String route=routeSpinner.getSelectedItem().toString();
        String totalSeat=totalSeatSpinner.getSelectedItem().toString();
        String seat1=seat1Spinner.getSelectedItem().toString();
        String seat2=seat2Spinner.getSelectedItem().toString();
        String seat3=seat3Spinner.getSelectedItem().toString();
        String season=seasonSpinner.getSelectedItem().toString();
        String dayNight=dayNightSpinner.getSelectedItem().toString();
        String ratings=ratingsSpinner.getSelectedItem().toString();

        Feedback feedback=new Feedback(route,totalSeat,seat1,seat2,seat3,season,dayNight,ratings);

        userReference.push().setValue(feedback);
        Toast.makeText(FeedbackActivity.this,"Feedback Accepted",Toast.LENGTH_SHORT).show();
    }
}

























