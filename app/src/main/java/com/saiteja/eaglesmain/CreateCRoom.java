package com.saiteja.eaglesmain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateCRoom extends AppCompatActivity {
    EditText name,email,phone,address,seats;
    String str_name,str_email,str_phone,str_address,str_seats;
    String  tag_string_add_conf = "req_add_conf";
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_croom);
        name = (EditText)findViewById(R.id.cf_name);
        email = (EditText)findViewById(R.id.cf_email);
        phone = (EditText)findViewById(R.id.cf_phone);
        address = (EditText)findViewById(R.id.cf_address);
        seats = (EditText)findViewById(R.id.cf_seats);
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid","");
     //   email = (EditText)findViewById(R.id.cf_email);
    }

    public void addConference(View view) {

        str_name=name.getText().toString();
        str_email=email.getText().toString();
        str_phone=phone.getText().toString();
        str_address=address.getText().toString();
        str_seats=seats.getText().toString();

        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.addConferenceRoom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(RegisterActivity.this,Login.class);
                startActivity(intent);*/
                try {
                    //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=jObj.getBoolean("error");
                    if(error)
                    {
                        String message=jObj.getString("message");
                        Toast.makeText(CreateCRoom.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String message=jObj.getString("message");
                        Toast.makeText(CreateCRoom.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error",e.getMessage());
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
                params.put("name",str_name );
                params.put("email",str_email );
                params.put("phone", str_phone);
                params.put("addr",str_address);
                params.put("uid", uid);
                params.put("seats", str_seats);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_add_conf);
        startActivity(new Intent(CreateCRoom.this,UpdateCRoom.class));
    }

    public void goToUpdate(View view) {
        startActivity(new Intent(this,UpdateCRoom.class));
    }
}



