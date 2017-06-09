package com.projects.thirtyseven.glue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTicketActivity extends AppCompatActivity implements View.OnClickListener {
    TextView ticketDate, ticketTime, ticketTag;
    EditText ticketCategory, ticketDescription, ticketTaskProfession, ticketTaskCoWorker,
            ticketTaskFee, ticketExpenses, ticketSpending, ticketComment, ticketTitle;
    Button save;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("tickets");

        init();
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ticket.setTicketTitle(ticketTitle.getText().toString());
        ticket.setTicketDate(ticketDate.getText().toString());
        ticket.setTicketTime(ticketTime.getText().toString());
        ticket.setTicketTag(ticketTag.getText().toString());
        ticket.setTicketCategory(ticketCategory.getText().toString());
        ticket.setTicketDescription(ticketDescription.getText().toString());
        ticket.setTicketTaskProfession(ticketTaskProfession.getText().toString());
        ticket.setTicketTaskCoWorker(ticketTaskCoWorker.getText().toString());
        ticket.setTicketTaskFee(ticketTaskFee.getText().toString());
        ticket.setTicketExpenses(ticketExpenses.getText().toString());
        ticket.setTicketSpending(ticketSpending.getText().toString());
        ticket.setTicketComment(ticketComment.getText().toString());

        databaseReference.push().setValue(ticket);
    }

    private void init() {
        ticket = new Ticket();
        ticketDate = (TextView) findViewById(R.id.ticketDateText);
        ticketTime = (TextView) findViewById(R.id.ticketTimeText);
        ticketTag = (TextView) findViewById(R.id.chooseTheTag);
        ticketTitle = (EditText) findViewById(R.id.ticketTitleText);
        ticketCategory = (EditText) findViewById(R.id.ticketCategoryText);
        ticketDescription = (EditText) findViewById(R.id.ticketDescriptionText);
        ticketTaskProfession = (EditText) findViewById(R.id.ticketProfessionText);
        ticketTaskCoWorker = (EditText) findViewById(R.id.ticketCoWorkerText);
        ticketTaskFee = (EditText) findViewById(R.id.ticketTaskFeeText);
        ticketExpenses = (EditText) findViewById(R.id.ticketExpensesNameText);
        ticketSpending = (EditText) findViewById(R.id.ticketSpendingText);
        ticketComment = (EditText) findViewById(R.id.ticketCommentText);
        save = (Button) findViewById(R.id.saveTicketButton);
    }

}
