package com.saiteja.eaglesmain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class EditSuperAdmin extends AppCompatActivity {

    EditText name,email,phone;
    Button update;
    String str_name,str_email,str_phone;
    String uid;
    String  tag_string_update_conf = "req_upd_admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_super_admin);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.mobile);
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid","");
        update = (Button)findViewById(R.id.update);
        //Toast.makeText(this, "Welcome ra Kishore", Toast.LENGTH_SHORT).show();
        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=false;/*jObj.getBoolean("error");*/
                    if(!error) {
                        str_name = jObj.getString("name");
                        str_email = jObj.getString("email");
                        Toast.makeText(getBaseContext(),""+str_email,Toast.LENGTH_LONG).show();
                        str_phone = jObj.getString("mobile");
                        String message = jObj.getString("message");
                        Toast.makeText(EditSuperAdmin.this, "" + message, Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        String message=jObj.getString("message");
                        Toast.makeText(EditSuperAdmin.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                name.setText(str_name);
                email.setText(str_email);
                phone.setText(str_phone);
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
                params.put("uid",uid );

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_update_conf);
    }

    public void updateUser(View view) {

        str_name=name.getText().toString();
        str_email=email.getText().toString();
        str_phone=phone.getText().toString();
        Toast.makeText(this, "Ajay", Toast.LENGTH_SHORT).show();

        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.editSuperAdminDetails , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(RegisterActivity.this,Login.class);
                startActivity(intent);*/
                try {
                    //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=jObj.getBoolean("error");
                    String message;
                    if(error)
                    {
                        message=jObj.getString("message");
                        Toast.makeText(EditSuperAdmin.this, message, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        message=jObj.getString("message");
                        Toast.makeText(EditSuperAdmin.this, message, Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getBaseContext(),""+message,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error",e.getMessage());
                }

                Intent intent  = new Intent(EditSuperAdmin.this,SuperAd.class);
                startActivity(intent);


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
                params.put("mobile", str_phone);
                params.put("uid",uid);
                params.put("ld","");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_update_conf);

    }
}
