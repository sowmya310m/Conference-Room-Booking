package com.saiteja.eaglesmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class UpdateCRoom extends AppCompatActivity {

    EditText name,email,phone,address,seats;
    Button update;
    String cid;
    String str_name,str_email,str_phone,str_address,str_seats,uid;
    String  tag_string_update_conf = "req_upd_conf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_croom);
        name = (EditText)findViewById(R.id.cf_up_name);
        email = (EditText)findViewById(R.id.cf_up_email);
        phone = (EditText)findViewById(R.id.cf_up_phone);
        address = (EditText)findViewById(R.id.cf_up_address);
        seats = (EditText)findViewById(R.id.cf_up_seats);
        update = (Button)findViewById(R.id.update);

        Intent intent=getIntent();
        cid=intent.getStringExtra("cid");
        Toast.makeText(this, "Welcome ra Kishore", Toast.LENGTH_SHORT).show();
        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.getConferenceRoom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=jObj.getBoolean("error");
                    if(!error) {
                        str_name = jObj.getString("cname");
                        str_email = jObj.getString("cemail");
                        str_phone = jObj.getString("cphone");
                        str_address = jObj.getString("caddr");
                        str_seats = jObj.getString("seats");
                        uid = jObj.getString("uid");
                        String message = jObj.getString("message");
                        Toast.makeText(UpdateCRoom.this, "" + message, Toast.LENGTH_LONG).show();
                        name.setText(str_name);
                        email.setText(str_email);
                        phone.setText(str_phone);
                        address.setText(str_address);
                        seats.setText(str_seats);
                    }
                    else
                    {
                        String message=jObj.getString("message");
                        Toast.makeText(UpdateCRoom.this, message, Toast.LENGTH_SHORT).show();
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
                params.put("cid",cid );

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "Yo Yo Honey Singh");
    }

    public void updateConference(View view) {

        str_name=name.getText().toString();
        str_email=email.getText().toString();
        str_phone=phone.getText().toString();
        str_address=address.getText().toString();
        str_seats=seats.getText().toString();

        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.updateConferenceRoom, new Response.Listener<String>() {
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
                        Toast.makeText(UpdateCRoom.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String message=jObj.getString("message");
                        Toast.makeText(UpdateCRoom.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateCRoom.this,AdminLogged.class));
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
                params.put("uid", ""+3);
                params.put("seats", str_seats);
                params.put("cid",cid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_update_conf);

    }
}
