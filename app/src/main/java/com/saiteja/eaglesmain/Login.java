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

public class Login extends AppCompatActivity {


//    public static String url_reg="http://192.168.0.5/eagles/login.php";
    EditText ed_email,ed_password;
    String email,password;
    String tag_string_req = "req_login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_email=(EditText)findViewById(R.id.email);
        ed_password=(EditText)findViewById(R.id.password);
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String status=sharedPreferences.getString("status","");
        if(status.equalsIgnoreCase("in")){
            String role=sharedPreferences.getString("role","");
            if(role.equalsIgnoreCase("admin"))
            {
                startActivity(new Intent(this,AdminLogged.class));
            }
            else if(role.equalsIgnoreCase("other"))
            {
                startActivity(new Intent(this,UserLogged.class));
            }
            else if(role.equalsIgnoreCase("super"))
            {
                startActivity(new Intent(this,SuperAd.class));
            }
        }

    }



    public void doRegister(View view) {
        startActivity(new Intent(Login.this,RegisterActivity.class));
    }

    public void doLogin(View view) {
        email=ed_email.getText().toString();
        password=ed_password.getText().toString();

        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(RegisterActivity.this,Login.class);
                startActivity(intent);*/
                try {
                    //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);
                    //Toast.makeText(RegisterActivity.this, "step 2", Toast.LENGTH_SHORT).show();

                    Boolean value=jObj.getBoolean("error");

                    //Toast.makeText(RegisterActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                    if(value){
                        String message=jObj.getString("message");
                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String message=jObj.getString("message");
                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                        String role = jObj.getString("role");
                        String ld = jObj.getString("ld");
                        String name = jObj.getString("name");
                        String mobile = jObj.getString("mobile");
                        String uid=jObj.getString("uid");
                        Toast.makeText(Login.this,"Role : "+role+" ld : "+ld+" name : "+name+" mobile : "+mobile+" UID"+uid,Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        final SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("role",role);
                        editor.putString("name",name);
                        editor.putString("mobile",mobile);
                        editor.putString("email",email);
                        editor.putString("uid",uid);
                        editor.putString("status","in");
                        editor.commit();

                        if(role.equalsIgnoreCase("admin")){
                            Intent i = new Intent(Login.this,AdminLogged.class);
                            startActivity(i);
                        }
                        else if(role.equalsIgnoreCase("other")){
                            Intent i = new Intent(Login.this,UserLogged.class);
                            startActivity(i);
                        }
                        else if(role.equalsIgnoreCase("super")){
                            Intent i = new Intent(Login.this,SuperAd.class);
                            startActivity(i);
                        }
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
                params.put("username", email);
                params.put("password",password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        finishAffinity();;
    }
}
