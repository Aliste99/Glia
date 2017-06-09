package com.projects.thirtyseven.glue;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTicketActivity extends AppCompatActivity {
    TextView ticketDate, ticketTime, ticketTag;
    EditText ticketCategory, ticketDescription, ticketTaskProfession, ticketTaskCoWorker,
            ticketTaskFee, ticketExpenses, ticketSpending, ticketComment;
    Button saveButton;

    int DIALOG_DATE = 1;
    int DIALOG_TIME = 1;
    final Calendar c = Calendar.getInstance();
    int myYear = c.get(Calendar.YEAR);
    int myMonth = c.get(Calendar.MONTH);
    int myDay = c.get(Calendar.DAY_OF_MONTH);
    int myHour = c.get(Calendar.HOUR);
    int myMinute = c.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        getSupportActionBar().hide();

        init();
        setCurrentTime();
        setAdapters();
    }

    private void setCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = format.format(c.getTime());
        ticketDate.setText(strDate);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String strTime = dateFormat.format(c.getTime());
        ticketTime.setText(strTime);
    }

    private void setAdapters() {
        ticketDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });
        ticketTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_TIME);
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallTimeBack, myHour, myMinute, true);
            return tpd;
        }
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallTimeBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            String hour;
            String fixMinute;
            if (myHour < 10) hour = String.valueOf("0" + myHour);
            else hour = String.valueOf(myHour);
            if (myMinute < 10) fixMinute = String.valueOf("0" + myMinute);
            else fixMinute = String.valueOf(myMinute);
            ticketTime.setText(hour + ":" + fixMinute);
        }
    };


    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear + 1;
            myDay = dayOfMonth;
            String day;
            String month;
            if (myDay < 10) day = "0" + myDay;
            else day = String.valueOf(myDay);
            if (myMonth < 10) month = "0" + myMonth;
            else month = String.valueOf(myMonth);
            ticketDate.setText(day + "." + month + "." + myYear);
        }
    };

    private void init() {
        ticketDate = (TextView) findViewById(R.id.ticketDateText);
        ticketTime = (TextView) findViewById(R.id.ticketTimeText);
        ticketTag = (TextView) findViewById(R.id.chooseTheTag);
        ticketCategory = (EditText) findViewById(R.id.ticketCategoryText);
        ticketDescription = (EditText) findViewById(R.id.ticketDescriptionText);
        ticketTaskProfession = (EditText) findViewById(R.id.ticketProfessionText);
        ticketTaskCoWorker = (EditText) findViewById(R.id.ticketCoWorkerText);
        ticketTaskFee = (EditText) findViewById(R.id.ticketTaskFeeText);
        ticketExpenses = (EditText) findViewById(R.id.ticketExpensesNameText);
        ticketSpending = (EditText) findViewById(R.id.ticketSpendingText);
        ticketComment = (EditText) findViewById(R.id.ticketCommentText);
        saveButton = (Button) findViewById(R.id.saveTicketButton);
    }

}
