package com.saiteja.eaglesmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class User_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home);
        SearchableSpinner searchableSpinner=(SearchableSpinner)findViewById(R.id.location);
        searchableSpinner.setTitle("Select Item");
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Select\t\t");
        arrayList.add("A plus");
        arrayList.add("A minus");
        arrayList.add("B plus");
        arrayList.add("B minus");
        arrayList.add("O plus");
        arrayList.add("O minus");
        arrayList.add("AB plus");
        arrayList.add("AB minus");
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);

        searchableSpinner.setAdapter(adapter);
        searchableSpinner.setPositiveButton("OK");
    }
}
