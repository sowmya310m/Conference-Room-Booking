package com.saiteja.eaglesmain;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Sai Teja on 22-03-2017.
 */

public class Fragment_2 extends Fragment {
    RadioButton btn1,btn2,btn3,btn4;
    Button reg;
    RadioGroup grp1,grp2;
    EditText sdate,edate;
    String sdte,edte,ssession,esession;
    FragmentListener2 activityCommander;
    public interface FragmentListener2{
        public void sendDate2(String sdte, String edte, String ssession);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            activityCommander = (FragmentListener2) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2,container,false);
        sdate = (EditText) view.findViewById(R.id.stdate);
        edate = (EditText)view.findViewById(R.id.edate);
        reg = (Button) view.findViewById(R.id.register);
        ssession="";
        esession="";
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sdte = sdate.getText().toString();
                edte = edate.getText().toString();
                activityCommander.sendDate2(sdte,edte,"fullday");

            }
        });
        return view;
    }
}

