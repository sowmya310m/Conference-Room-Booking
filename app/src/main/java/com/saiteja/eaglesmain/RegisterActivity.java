package com.saiteja.eaglesmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText ed_name,ed_email,ed_pword,ed_cpword,ed_phone;
    Spinner spinner;
    String email,name,pword,cpword,user,phone,ministry;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    //public static String url_reg="http://192.168.0.5/eagles/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_name = (EditText)findViewById(R.id.reg_name);
        ed_email = (EditText)findViewById(R.id.reg_mail);
        ed_pword = (EditText)findViewById(R.id.reg_pwd);
        ed_cpword = (EditText)findViewById(R.id.reg_cpwd);
        ed_phone = (EditText)findViewById(R.id.reg_phone);
        spinner = (Spinner) findViewById(R.id.spinner);
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
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void updateDB(View view){
        String tag_string_req = "req_register";
        email = ed_email.getText().toString();
        phone = ed_phone.getText().toString();
        name = ed_name.getText().toString();
        pword = ed_pword.getText().toString();
        cpword = ed_cpword.getText().toString();
        //RadioButton type = (RadioButton)findViewById(userType.getCheckedRadioButtonId());
        user = "other";
        if(!TextUtils.isEmpty(name)){
            if(phone.length()>=10&&phone.length()<=13) {
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                if (matcher.find()) {
                    if (!TextUtils.isEmpty(pword) && !TextUtils.isEmpty(cpword)) {
                        if (pword.length() >= 8) {
                            if(cpword.length()>=8){
                                if(pword.equals(cpword)) {
                                    ministry = spinner.getSelectedItem().toString();
                                    if(!TextUtils.isEmpty(ministry)){
                                        Toast.makeText(this, ministry, Toast.LENGTH_SHORT).show();
                                    }

                                        //startActivity(new Intent(this, AdminActivity.class));
                                    Toast.makeText(this, ministry, Toast.LENGTH_SHORT).show();
                                        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.register, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(RegisterActivity.this, Login.class);
                                                startActivity(intent);


                                                /*try {
                                                    //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                                                    JSONObject jObj = new JSONObject(response);
                                                    Toast.makeText(RegisterActivity.this, "step 2", Toast.LENGTH_SHORT).show();

                                                    Boolean value=jObj.getBoolean("error");

                                                    Toast.makeText(RegisterActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                                                    if(value)
                                                        Toast.makeText(RegisterActivity.this, "Data is pushed successfully", Toast.LENGTH_SHORT).show();
                                                    else
                                                        Toast.makeText(RegisterActivity.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Log.e("Error",e.getMessage());
                                                }*/


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
                                                params.put("email", email);
                                                params.put("mobile", phone);
                                                params.put("password", pword);
                                                params.put("role", "other");
                                                params.put("ld",ministry);
                                                return params;
                                            }
                                        };
                                        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                                }
                                else {
                                    Toast.makeText(getBaseContext(),"Passwords Did'nt match",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(getBaseContext(),"Password should be minimum of 8 characters",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getBaseContext(),"Password should be minimum of 8 Characters",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Password should be minimum of 8 Characters",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(getBaseContext(),"Enter Valid Email Address",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getBaseContext(),"Please Enter valid Phone Number",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getBaseContext(),"Please Enter your Name",Toast.LENGTH_LONG).show();
        }
    }
}
