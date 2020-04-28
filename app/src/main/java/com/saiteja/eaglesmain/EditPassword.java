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

public class EditPassword extends AppCompatActivity {
    EditText pword,npword,cpword;
    String password,newpassword,uid;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        pword = (EditText)findViewById(R.id.pword);
        npword = (EditText)findViewById(R.id.npword);
        cpword = (EditText)findViewById(R.id.cpword);
        button = (Button)findViewById(R.id.change);
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid","");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePword(v);
            }
        });
    }
    public void changePword(View v){
        password=pword.getText().toString();
        newpassword=npword.getText().toString();

        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.editPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();


                try {
                    //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=jObj.getBoolean("error");
                    if(error)
                    {
                        Toast.makeText(EditPassword.this, jObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Toast.makeText(EditPassword.this, jObj.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(EditPassword.this,Login.class);
                        Toast.makeText(EditPassword.this, "Please login again to apply changes..!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
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
                params.put("uid",uid);
                params.put("password",password );
                params.put("npassword",newpassword);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "Update pwd");

    }
}
