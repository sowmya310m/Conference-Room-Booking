package com.saiteja.eaglesmain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ViewBookingsUser extends AppCompatActivity {


    final ArrayList<String> details=new ArrayList<String>();
    JSONArray jsonArray;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings_user);
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid","");
        getDetails();
    }

    public void getDetails()
    {


        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.viewUserBooking , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(RegPage.this, "Hai", Toast.LENGTH_SHORT).show();




                try {
                    //Toast.makeText(RegPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=jObj.getBoolean("error");
                    //jsonArray=jObj.getJSONArray("bookings");
                    if(!error) {
                        jsonArray=jObj.getJSONArray("bookings");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject result = jsonArray.getJSONObject(i);
                        /*Toast.makeText(ListViews.this, result.getString("cname"), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ListViews.this, result.getString("cemail"), Toast.LENGTH_SHORT).show();
                        result.getString("caddr")+" "+result.getString("cname")*/
                            details.add(result.getString("sdate") + " to " + result.getString("edate"));
                        }
                        showList();
                    }
                    else
                    {
                        Toast.makeText(ViewBookingsUser.this, jObj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("uid", uid );
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "Get details");
    }

    public void showList()
    {
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,details);
        final ListView listView=(ListView)findViewById(R.id.viewBookings);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewBookingsUser.this, arrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ViewBookingsUser.this,ViewBookingDetails.class);
                intent.putExtra("cid",arrayAdapter.getItem(position));
                try {
                    intent.putExtra("data",  jsonArray.getJSONObject(position).toString());
                }
                catch (JSONException e){}
                startActivity(intent);
            }
        });
    }
}
