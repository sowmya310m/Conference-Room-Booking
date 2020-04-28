package com.saiteja.eaglesmain;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Sai Teja on 22-03-2017.
 */

public class Fragment_Button extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_button,container,false);
        Button button2=(Button)view
                .findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Fragment_2 fragment_2=new Fragment_2();
                fragmentTransaction.replace(R.id.fragment_id_2,fragment_2);
                fragmentTransaction.commit();
            }
        });
        Button button1=(Button)view
                .findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Fragment_1 fragment_1=new Fragment_1();
                fragmentTransaction.replace(R.id.fragment_id_2,fragment_1);
                fragmentTransaction.commit();
            }
        });
        return view;
    }


}
