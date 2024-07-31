package com.example.dutysync;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class Assignduty extends Fragment {
    View view;
    EditText editText_name,editText_date;
    TextView textView;
    Button button_assignDuty;
    Calendar mycalendar=Calendar.getInstance();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_assignduty, container, false);
        editText_name= (EditText) view.findViewById(R.id.dropdown);
        editText_date= (EditText) view.findViewById(R.id.datepicker);
        textView= (TextView) view.findViewById(R.id.displaytext);
        button_assignDuty= (Button) view.findViewById(R.id.assign);


        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new  DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mycalendar.set(Calendar.YEAR,i);
                        mycalendar.set(Calendar.MONTH,i1);
                        mycalendar.set(Calendar.DAY_OF_MONTH,i2);

                        String myFormat="dd-MMM-yyyy";
                        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                        editText_date.setText(dateFormat.format(mycalendar.getTime()));
                    }
                }, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH), mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        button_assignDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText_name.getText().toString();
                String date=editText_date.getText().toString();
                if (name.isEmpty()){
                    editText_name.setError("Please Select Name");
                } else if (date.isEmpty()) {
                    editText_date.setError("Please Select Date");

                }else {
                    Toast.makeText(getContext(), "Duty Assign Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}