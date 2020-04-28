package com.saiteja.eaglesmain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditUser extends AppCompatActivity {

    Spinner spinner;
    EditText ed_name,ed_mail,ed_phone;
    String str_name,str_email,str_phone,str_ld;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        ed_name=(EditText)findViewById(R.id.ue_name);
        ed_mail=(EditText)findViewById(R.id.ue_email);
        ed_phone=(EditText)findViewById(R.id.ue_mobile);


        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        ed_name.setText(sharedPreferences.getString("name",""));
        ed_mail.setText(sharedPreferences.getString("email",""));
        ed_phone.setText(sharedPreferences.getString("mobile",""));

        uid=sharedPreferences.getString("uid","");


        spinner = (Spinner) findViewById(R.id.ue_spinner);
        ArrayList<String> alist= new ArrayList<>();
        alist.add("Ministry of Agriculture and Farmers Welfare");
        alist.add("Ministry of AYUSH");
        alist.add("Ministry of Chemicals and Fertilizers");
        alist.add("Ministry of Civil Aviation");
        alist.add("Ministry of Coal");
        alist.add("Ministry of Commerce and Industry");
        alist.add("Ministry of Communications");
        alist.add("Ministry of Consumer Affairs, Food and Public Distribution");
        alist.add("Ministry of Corporate Affairs");
        alist.add("Ministry of Culture");
        alist.add("Ministry of Defence");
        alist.add("Ministry of Development of North Eastern Region");
        alist.add("Ministry of Drinking Water and Sanitation");
        alist.add("Ministry of Earth Sciences");
        alist.add("Ministry of Electronics and Information Technology");
        alist.add("Ministry of Environment, Forest and Climate Change");
        alist.add("Ministry of External Affairs");
        alist.add("Ministry of Finance");
        alist.add("Ministry of Food Processing Industries");
        alist.add("Ministry of Health and Family Welfare");
        alist.add("Ministry of Heavy Industries and Public Enterprises");
        alist.add("Ministry of Home Affairs");
        alist.add("Ministry of Housing and Urban Poverty Alleviation");
        alist.add("Ministry of Human Resource Development");
        alist.add("Ministry of Information and Broadcasting");
        alist.add("Ministry of Labour and Employment");
        alist.add("Ministry of Law and Justice");
        alist.add("Ministry of Micro, Small and Medium Enterprises");
        alist.add("Ministry of Mines");
        alist.add("Ministry of Minority Affairs");
        alist.add("Ministry of New and Renewable Energy");
        alist.add("Ministry of Panchayati Raj");
        alist.add("Ministry of Parliamentary Affairs");
        alist.add("Ministry of Personnel, Public Grievances and Pensions");
        alist.add("Ministry of Petroleum and Natural Gas");
        alist.add("Ministry of Power");
        alist.add("Ministry of Railways");
        alist.add("Ministry of Road Transport and Highways");
        alist.add("Ministry of Rural Development");
        alist.add("Ministry of Science and Technology");
        alist.add("Ministry of Shipping");
        alist.add("Ministry of Skill Development and Entrepreneurship");
        alist.add("Ministry of Social Justice and Empowerment");
        alist.add("Ministry of Statistics and Programme Implementation");
        alist.add("Ministry of Steel");
        alist.add("Ministry of Tourism");
        alist.add("Ministry of Tribal Affairs");
        alist.add("Ministry of Urban Development");
        alist.add("Ministry of Water Resources, River Development and Ganga Rejuvenation");
        alist.add("Ministry of Women and Child Development");
        alist.add("Ministry of Youth Affairs and Sports");
        ArrayAdapter<String> adapter= new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,alist);

        int pos=adapter.getPosition(sharedPreferences.getString("ld",""));
        spinner.setSelection(pos);

        spinner.setAdapter(adapter);
    }


    public void updateDetails(View view) {
        str_name=ed_name.getText().toString();
        str_email=ed_mail.getText().toString();
        str_phone=ed_phone.getText().toString();
        str_ld=spinner.getSelectedItem().toString();
        Toast.makeText(this, "Ajay", Toast.LENGTH_SHORT).show();

        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.editUserDetails, new Response.Listener<String>() {
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
                        Toast.makeText(EditUser.this, message, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        message=jObj.getString("message");
                        Toast.makeText(EditUser.this, message, Toast.LENGTH_LONG).show();
                        Toast.makeText(EditUser.this,"Please Login again to update details..!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(EditUser.this,Login.class);
                        startActivity(i);

                    }
                    Toast.makeText(getBaseContext(),""+message,Toast.LENGTH_LONG).show();
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
                params.put("mobile", str_phone);
                params.put("ld",str_ld);
                params.put("uid",uid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "edit_user");

    }
}
