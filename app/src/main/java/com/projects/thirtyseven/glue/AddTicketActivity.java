package com.projects.thirtyseven.glue;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddTicketActivity extends AppCompatActivity {
    TextView ticketDate, ticketTime, ticketTag;
    EditText ticketCategory, ticketDescription, ticketTaskProfession, ticketTaskCoWorker,
            ticketTaskFee, ticketExpenses, ticketSpending, ticketComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        getSupportActionBar().hide();

        init();
    }

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
    }

}
