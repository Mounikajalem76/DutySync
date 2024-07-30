package com.example.dutysync;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import java.util.Objects;


public class Assignduty extends Fragment {
    View view;
    EditText editText_selectname,editText_selctdate;
    TextView textView;
    Button button_submit;
    DatePickerDialog.OnDateSetListener dateSetListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_assignduty, container, false);
        editText_selectname= (EditText) view.findViewById(R.id.selectname);
        editText_selctdate= (EditText) view.findViewById(R.id.selectdate);

        textView= (TextView) view.findViewById(R.id.text);
        button_submit= (Button) view.findViewById(R.id.submit);
        editText_selctdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                dateSetListener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        editText_selctdate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new DatePickerDialog(requireContext(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=editText_selectname.getText().toString();
                String date=editText_selctdate.getText().toString();
               if (date.isEmpty()){
                   editText_selctdate.setError("Please select date");
               }else{
                   Toast.makeText(getContext(), "Duty Assign Successfully", Toast.LENGTH_SHORT).show();
               }

                    }
        });
        return view;
    }
}