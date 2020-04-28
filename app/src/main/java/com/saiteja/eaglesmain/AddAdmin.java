package com.saiteja.eaglesmain;

import android.content.Intent;
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

public class AddAdmin extends AppCompatActivity {

    EditText ed_name,ed_mail,ed_location,ed_password,ed_cnf_password,ed_mobile;
    String name,mail,location,password,cnf_password,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        ed_name=(EditText)findViewById(R.id.ad_name);
        ed_location=(EditText)findViewById(R.id.ad_location);
        ed_mail=(EditText)findViewById(R.id.ad_email);
        ed_mobile=(EditText)findViewById(R.id.ad_phone);
        ed_password=(EditText)findViewById(R.id.ad_password);
        ed_cnf_password=(EditText)findViewById(R.id.ad_cnf_password);
    }

    public void addAdmin(View view) {
        String tag_string_req = "req_register";
        name=ed_name.getText().toString();
        location=ed_location.getText().toString();
        mail=ed_mail.getText().toString();
        mobile=ed_mobile.getText().toString();
        password=ed_password.getText().toString();
        cnf_password=ed_cnf_password.getText().toString();

        if(password.equals(cnf_password)){
            StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.register, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();




                    try {
                        //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                        JSONObject jObj = new JSONObject(response);

                        Boolean value=jObj.getBoolean("error");
                        String message=jObj.getString("message");

                        if(value) {
                            Toast.makeText(AddAdmin.this, message, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AddAdmin.this, message, Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(AddAdmin.this, SuperAd.class);
                    startActivity(intent);

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
                    params.put("name", name);
                    params.put("email", mail);
                    params.put("mobile", mobile);
                    params.put("password", password);
                    params.put("role", "admin");
                    params.put("ld",location);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

    }
}
