package com.saiteja.eaglesmain;

import android.content.Intent;
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

public class AvailableConf extends AppCompatActivity {

    String sess,stdate,endate,location;
    JSONArray jsonArray;
    final ArrayList<String> details=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_conf);
        Intent intent=getIntent();
        location=intent.getStringExtra("location");
        stdate=intent.getStringExtra("stdate");
        endate=intent.getStringExtra("endate");
        sess=intent.getStringExtra("session");
        getData();
    }

    public void getData()
    {
        StringRequest strReq=new StringRequest(Request.Method.POST, AppConfig.booking , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jObj = new JSONObject(response);
                    Boolean error=jObj.getBoolean("error");
                    if(error) {
                        String message = jObj.getString("message");
                        Toast.makeText(AvailableConf.this, "" + message, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //String message=jObj.getString("message");
                        //Toast.makeText(AvailableConf.this, message, Toast.LENGTH_SHORT).show();
                        jsonArray=jObj.getJSONArray("halls");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject result=jsonArray.getJSONObject(i);
                        /*Toast.makeText(ListViews.this, result.getString("cname"), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ListViews.this, result.getString("cemail"), Toast.LENGTH_SHORT).show();
                        result.getString("caddr")+" "+result.getString("cname")*/
                            details.add(result.getString("cname"));
                        }
                        showList();
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
                params.put("city",location );
                params.put("date1",stdate);
                params.put("date2",endate);
                params.put("session",sess);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "available_conf");
    }

   public void showList()
   {
       final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,details);
       final ListView listView=(ListView)findViewById(R.id.availableConf);
       listView.setAdapter(arrayAdapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               try {
                   JSONObject result = jsonArray.getJSONObject(position);
                   //Toast.makeText(AvailableConf.this, position, Toast.LENGTH_SHORT).show();
                   Toast.makeText(AvailableConf.this, result.getString("cid"), Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(AvailableConf.this,DisplayConferenceDetails.class);
                   intent.putExtra("cid",result.getString("cid"));
                   intent.putExtra("caddr",result.getString("caddr"));
                   intent.putExtra("cemail",result.getString("cemail"));
                   intent.putExtra("cphone",result.getString("cphone"));
                   intent.putExtra("seats",result.getString("seats"));
                   intent.putExtra("name",result.getString("cname"));
                   intent.putExtra("location",location);
                   intent.putExtra("session",sess);
                   intent.putExtra("stdate",stdate);
                   intent.putExtra("endate",endate);
                   startActivity(intent);
               }
               catch (JSONException e){

               }
           }
       });
   }
}
