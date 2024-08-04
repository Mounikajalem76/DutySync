package com.example.dutysync;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class Postattendence extends Fragment {
    View view;
    EditText editText_date;
    TextView textView;
    Button button_present;
    AutoCompleteTextView autoCompleteTextView;
    Calendar mycalendar=Calendar.getInstance();
    String name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_postattendence, container, false);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.post_dropdown);
        editText_date= (EditText) view.findViewById(R.id.post_datepicker);
        textView= (TextView) view.findViewById(R.id.post_displaytext);
        button_present= (Button) view.findViewById(R.id.present);









        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mycalendar.set(Calendar.YEAR,i);
                        mycalendar.set(Calendar.MONTH,i1);
                        mycalendar.set(Calendar.DAY_OF_MONTH,i2);
                        String myFormat="dd-MMM-yyyy";
                        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                        editText_date.setText(dateFormat.format(mycalendar.getTime()));



                        List<String> teamList=new ArrayList<>();
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("AssignDuty");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //Toast.makeText(getContext(), ""+editText_date.getText().toString(), Toast.LENGTH_SHORT).show();
                                if (isAdded()){
                                if (snapshot.exists()){
                                    for (DataSnapshot dateSnapshot : snapshot.getChildren()) {

                                        String dateAssin = dateSnapshot.getKey();
                                        if (dateAssin.equals(editText_date.getText().toString())){
                                            for (DataSnapshot  nameSnapshot: dateSnapshot.getChildren()) {
                                                String nameAssign = nameSnapshot.getKey();
                                                // Toast.makeText(getContext(), ""+nameAssign, Toast.LENGTH_SHORT).show();
                                                teamList.add(nameAssign);
                                            }

                                            }

                                        }
                                        // Toast.makeText(getContext(), ""+dutyAssin, Toast.LENGTH_SHORT).show();
                                        // teamList.add(name);
                                        //  Toast.makeText(getContext(), "Attending"+teamList, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                StringBuilder builder=new StringBuilder();
                                for (String names:teamList){
                                    builder.append(names).append("\n");

                                }
                                String names=builder.toString().trim();

                                String[] finalList=names.split("\n");
                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,finalList);
                                autoCompleteTextView.setAdapter(arrayAdapter);

                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        name = adapterView.getItemAtPosition(i).toString();
                                        //Toast.makeText(getContext(), "Select :"+name, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                if (isAdded()) {
                                    Toast.makeText(getContext(), "Data failed" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                }, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH), mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        button_present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=autoCompleteTextView.getText().toString();
                String date=editText_date.getText().toString();
                if (date.isEmpty()){
                    autoCompleteTextView.setError("Please Select Date");
                } else if (name.isEmpty()) {
                    editText_date.setError("Please Select Name");

                }else {

                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("AssignDuty").child(editText_date.getText().toString());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (isAdded()) {
                                reference.child(name).setValue("Present");
                                autoCompleteTextView.getText().clear();
                                Toast.makeText(getContext(), "Attendance Posted Successfully", Toast.LENGTH_SHORT).show();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            if (isAdded()){
                                Toast.makeText(getContext(), "DataStoring Failed" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    //editText_date.getText().clear();


                    //Toast.makeText(getContext(), name+":"+editText_date.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}