package com.tanvir.tt.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyRequest {

    private RequestQueue requestQueue;
    private String url = "http://localhost:9999/api/v1/bank/transfer-money";

    public VolleyRequest(Context context) {
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(context);
    }

    public void makePutRequest(String sourceAccountNumber, String targetAccountNumber, double amount,
                               Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        try {
            // Create JSON object with the request parameters
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("sourceAccountNumber", sourceAccountNumber);
            jsonBody.put("targetAccountNumber", targetAccountNumber);
            jsonBody.put("amount", amount);

            // Request a JSONObject response using PUT method.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonBody,
                    successListener,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error response and print stack trace
                            Log.e("VolleyRequest", "Error in API request", error);
                            errorListener.onErrorResponse(error);
                        }
                    }
            );

            // Add the request to the RequestQueue.
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            // Handle JSON exception
            e.printStackTrace();
        }
    }
}
