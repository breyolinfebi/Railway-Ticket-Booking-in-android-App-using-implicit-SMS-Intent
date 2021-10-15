package com.example.railwayticket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.Calendar;

import static android.service.controls.ControlsProviderService.TAG;

public class BookTicket extends AppCompatActivity {

    EditText mobno;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Spinner spintype,spinclass,spinname,spinfrom,spinto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        mobno = (EditText)findViewById(R.id.number_to_msg);
        spintype = findViewById(R.id.type_spinner);
        spinclass=findViewById(R.id.class_spinner);
        spinname = findViewById(R.id.tname);
        spinfrom=findViewById(R.id.tfrom);
        spinto=findViewById(R.id.tto);

        popSpintype();
        popSpinclass();
        popSpinname();
        popSpinfrom();
        popSpinto();

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

    }


    public void smsSendMessage(View view) {

        String type = spintype.getSelectedItem().toString();
        String cls = spinclass.getSelectedItem().toString();
        String name = spinname.getSelectedItem().toString();
        String from = spinfrom.getSelectedItem().toString();
        String to = spinto.getSelectedItem().toString();
        String d = getTodaysDate();

        String smsNumber = String.format("smsto: %s", mobno.getText().toString());
        String sms = "Your railway Ticket is confirmed" + " from " + from +
                "to " + to ;
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse(smsNumber));
        smsIntent.putExtra("sms_body", sms);
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_SENDTO Intent.");
        }
    }
    private void popSpintype() {
        ArrayAdapter<String> typeAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.train_type));
        typeAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(typeAdap);
    }

    private void popSpinclass() {
        ArrayAdapter<String> classAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.train_class));
        classAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinclass.setAdapter(classAdap);
    }

    private void popSpinname(){
        ArrayAdapter<String> nameadap = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.train_name));
        nameadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinname.setAdapter(nameadap);
    }

    private void popSpinfrom(){
        ArrayAdapter<String> fromAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.from));
        fromAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinfrom.setAdapter(fromAdap);
    }
    private void popSpinto() {
        ArrayAdapter<String> toAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.to));
        toAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinto.setAdapter(toAdap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_book_ticket, menu);
        return true;
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}