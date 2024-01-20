package com.tanvir.tt.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tanvir.tt.R;
import com.tanvir.tt.model.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    EditText amount, sourceAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        amount = findViewById(R.id.amount);
        sourceAccount = findViewById(R.id.source_account);

        Button paymentButton = findViewById(R.id.create_account_btn);

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String targetAccountNumber = "2459634474004165186";
                String sourceAccountNumber = sourceAccount.getText().toString();
                String amountString = amount.getText().toString();

                if (sourceAccountNumber.isEmpty() || amountString.isEmpty()) {
                    showToast("Please enter both source account number and amount.");
                    return;
                }

                double amountValue;
                try {
                    amountValue = Double.parseDouble(amountString);
                } catch (NumberFormatException e) {
                    showToast("Invalid amount format. Please enter a valid number.");
                    return;
                }

                makeVolleyRequest(sourceAccountNumber, targetAccountNumber, amountValue);
            }
        });
    }

    private void makeVolleyRequest(String sourceAccountNumber, String targetAccountNumber, double amount) {
        VolleyRequest volleyRequest = new VolleyRequest(this);

        volleyRequest.makePutRequest(
                sourceAccountNumber,
                targetAccountNumber,
                amount,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleVolleySuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyErrorResponse(error);
                    }
                });
    }

    private void handleVolleySuccessResponse(JSONObject response) {
        showToast("Payment Received Successfully");
        // Handle the success response as needed
        // You can parse and process the JSONObject 'response' here
    }

    private void handleVolleyErrorResponse(VolleyError error) {
        showToast("Failed to make payment. Please try again later. Error: " + error.getMessage());
        error.printStackTrace(); // Print the stack trace for more details
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
