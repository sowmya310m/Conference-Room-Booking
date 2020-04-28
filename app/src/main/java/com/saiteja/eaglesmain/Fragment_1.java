package com.saiteja.eaglesmain;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Sai Teja on 26-12-2016.
 */

public class Fragment_1 extends Fragment{
    CheckBox btn1,btn2;
    Button reg;
    String dte,session;
    EditText date;
    int year,month,day;
    Calendar calendar;
    FragmentListener1 activityCommander;
    Button datePick;


    public interface FragmentListener1{
        public void sendDate(String dte, String session);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            activityCommander = (FragmentListener1) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        date = (EditText) view.findViewById(R.id.date);
        btn1 = (CheckBox) view.findViewById(R.id.am);
        btn2 = (CheckBox) view.findViewById(R.id.pm);
        reg = (Button) view.findViewById(R.id.register);
        calendar= java.util.Calendar.getInstance();
        year=calendar.get(java.util.Calendar.YEAR);
        month=calendar.get(java.util.Calendar.MONTH);
        day=calendar.get(java.util.Calendar.DAY_OF_MONTH);
        datePick=(Button)view.findViewById(R.id.dateSelector);

        session = "";
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn1.isChecked()&&btn2.isChecked()){
                    session = "fullday";
                }
                else if(btn1.isChecked()){
                    session = "morning";
                }
                else if(btn2.isChecked()){
                    session = "afternoon";
                }
                else{
                }
                dte = date.getText().toString();
                activityCommander.sendDate(dte,session);
            }
        });

        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hai", Toast.LENGTH_SHORT).show();
                //getActivity().showDialog(999);
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        return view;
    }

    /*protected DatePickerDialog.OnDateSetListener dateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date.setText(dayOfMonth+"-"+(month+1)+"-"+year);
        }


    };
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==999){
            return new DatePickerDialog(getActivity(),dateListener,year,month,day);
        }
        return null;
    }*/





}
