package com.saiteja.eaglesmain;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User_Main extends AppCompatActivity implements Fragment_1.FragmentListener1, Fragment_2.FragmentListener2{
    Spinner spinner;
    String var;
    String sess,stdate,endate,location;
    ArrayList<String> arrayList=new ArrayList<>();
    JSONArray jsonArray;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);
        /*arrayList.add("KPHB");
        arrayList.add("Nizampet");
        arrayList.add("Hyderabad90");*/
        getLocations();
        spinner = (Spinner)findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                var=adapter.getItem(i);
                Toast.makeText(User_Main.this, var, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        Fragment_Button fragment_Button=new Fragment_Button();
        Fragment_1 fragment_1=new Fragment_1();
        fragmentTransaction.add(R.id.fragment_id_1,fragment_Button);
        fragmentTransaction.add(R.id.fragment_id_2,fragment_1);

        fragmentTransaction.commit();
    }

    @Override
    public void sendDate(String dte, String session) {
        Toast.makeText(this,"Date is "+dte,Toast.LENGTH_LONG).show();
        stdate=dte;
        endate=dte;
        if(session.equals("fullday")){
            Toast.makeText(this, "Sessions Selected are Morning and Afternoon", Toast.LENGTH_LONG).show();
            sess="fullday";
        }
        else if(session.equals("morning")){
            Toast.makeText(this, "Session Selected is Morning", Toast.LENGTH_LONG).show();
            sess="morning";
        }
        else if(session.equals("afternoon")){
            Toast.makeText(this, "Session Selected is Afternoon", Toast.LENGTH_LONG).show();
            sess="afternoon";
        }
        else{
        }
        Toast.makeText(getBaseContext(),"Location Selected for Conference is "+var,Toast.LENGTH_LONG).show();
        location=var;

        Intent intent=new Intent(this,AvailableConf.class);
        intent.putExtra("location",location);
        intent.putExtra("session",sess);
        intent.putExtra("stdate",stdate);
        intent.putExtra("endate",endate);
        startActivity(intent);

    }

    @Override
    public void sendDate2(String sdte, String edte, String ssession) {
        Toast.makeText(this, "Starting Date is "+sdte, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Ending Date is "+edte, Toast.LENGTH_LONG).show();
        stdate=sdte;
        endate=edte;
        Toast.makeText(this, "Session starts on "+ssession+" of First Day",Toast.LENGTH_LONG).show();
        sess="fullday";
        Toast.makeText(getBaseContext(),"Location Selected for Conference is "+var,Toast.LENGTH_LONG).show();
        location=var;
        Intent intent=new Intent(this,AvailableConf.class);
        intent.putExtra("location",location);
        intent.putExtra("session",sess);
        intent.putExtra("stdate",stdate);
        intent.putExtra("endate",endate);
        startActivity(intent);
    }

    public void getLocations()
    {
        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.getLocations, new Response.Listener<String>() {
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

                    }
                    else{
                        jsonArray=jObj.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject result=jsonArray.getJSONObject(i);
                        /*Toast.makeText(ListViews.this, result.getString("cname"), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ListViews.this, result.getString("cemail"), Toast.LENGTH_SHORT).show();
                        result.getString("caddr")+" "+result.getString("cname")*/
                            arrayList.add(result.getString("ld"));
                        }
                        adapter=new ArrayAdapter<String>(User_Main.this,android.R.layout.simple_dropdown_item_1line,arrayList);
                        spinner.setAdapter(adapter);
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
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "Get Locations");
    }
}

