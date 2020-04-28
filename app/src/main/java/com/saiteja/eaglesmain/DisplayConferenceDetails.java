package com.saiteja.eaglesmain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DisplayConferenceDetails extends AppCompatActivity {
    String cname,caddr,seats,cemail,cid,cphone,uid;
    TextView ed_cname,ed_caddr,ed_seats,ed_cemail,ed_phone;
    String sess,stdate,endate,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_conference_details);
        ed_cname=(TextView)findViewById(R.id.cname);
        ed_caddr=(TextView)findViewById(R.id.caddr);
        ed_cemail=(TextView)findViewById(R.id.cemail);
        ed_phone=(TextView)findViewById(R.id.cphone);
        ed_seats=(TextView)findViewById(R.id.seats);

        Intent intent=getIntent();
        cname=intent.getStringExtra("name");
        caddr=intent.getStringExtra("caddr");
        seats=intent.getStringExtra("seats");
        cemail=intent.getStringExtra("cemail");
        cid=intent.getStringExtra("cid");
        cphone=intent.getStringExtra("cphone");
        location=intent.getStringExtra("location");
        stdate=intent.getStringExtra("stdate");
        endate=intent.getStringExtra("endate");
        sess=intent.getStringExtra("session");
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid","");

        ed_cname.setText("Name:"+cname);
        ed_caddr.setText("Address:"+caddr);
        ed_phone.setText("Phone:"+cphone);
        ed_seats.setText("Seats:"+seats);
        ed_cemail.setText("Email:"+cemail);

    }

    public void confirmBooking(View view) {

        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.saveBooking , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=jObj.getBoolean("error");
                    if(error) {
                        String message = jObj.getString("message");
                        Toast.makeText(DisplayConferenceDetails.this, "" + message, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String message = jObj.getString("message");
                        startActivity(new Intent(DisplayConferenceDetails.this,UserLogged.class));
                        Toast.makeText(DisplayConferenceDetails.this, "" + message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("hghg", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Hai"+error.getMessage(), Toast.LENGTH_LONG).show();
                //Log.e("Error", error.getMessage());
            }
        }


        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("city",location );
                params.put("date1",stdate);
                params.put("date2",endate);
                params.put("session",sess);
                params.put("cid",cid);
                params.put("uid",uid);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "available_conf");
    }
}
