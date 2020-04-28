package com.saiteja.eaglesmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewBookingDetails extends AppCompatActivity {

    JSONObject result;
    String bid;
    TextView tv_cname,tv_stdate,tv_endate,tv_status,tv_session;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking_details);
        tv_cname=(TextView)findViewById(R.id.c_name);
        tv_stdate=(TextView)findViewById(R.id.c_stdate);
        tv_endate=(TextView)findViewById(R.id.c_endate);
        tv_session=(TextView)findViewById(R.id.c_session);
        tv_status=(TextView)findViewById(R.id.c_status);
        Intent intent=getIntent();
        try {
            result = new JSONObject(intent.getStringExtra("data"));
            /*Toast.makeText(this, result.getString("fs"), Toast.LENGTH_SHORT).show();*/
            bid=result.getString("id");
            tv_cname.setText("CName:"+result.getString("name"));
            tv_status.setText("Status:"+result.getString("status"));
            tv_session.setText("Session:"+result.getString("fs"));
            tv_stdate.setText("Start Date:"+result.getString("sdate"));
            tv_endate.setText("Ending Date:"+result.getString("edate"));
        }
        catch (JSONException e)
        {

        }
    }

    public void cancelBooking(View view) {
        if (!tv_status.getText().toString().contains("Cancelled")) {
            StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.cancelBooking, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(RegisterActivity.this,Login.class);
                startActivity(intent);*/
                    try {
                        //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                        JSONObject jObj = new JSONObject(response);
                        //Toast.makeText(RegisterActivity.this, "step 2", Toast.LENGTH_SHORT).show();
                        Boolean value = jObj.getBoolean("error");
                        if (value) {
                            Toast.makeText(ViewBookingDetails.this, jObj.getString("message") + " Or Please try again later", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewBookingDetails.this, jObj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(ViewBookingDetails.this,UserLogged.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Error", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("hghg", "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Hai" + error.getMessage(), Toast.LENGTH_LONG).show();
                    //Log.e("Error", error.getMessage());
                }
            }


            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("bid", bid);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq, "Cancel Booking");
        }
        else
        {
            Toast.makeText(this, "This booking is already Cancelled..!", Toast.LENGTH_SHORT).show();
        }
    }
}
