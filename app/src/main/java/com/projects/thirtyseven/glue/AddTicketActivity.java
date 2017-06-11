package com.projects.thirtyseven.glue;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTicketActivity extends AppCompatActivity{
    TextView ticketDate, ticketTime, ticketTag, ticketAttachments;
    EditText ticketCategory, ticketDescription, ticketTaskProfession, ticketTaskCoWorker,
            ticketTaskFee, ticketExpenses, ticketSpending, ticketComment, ticketTitle;
    Button saveButton;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Ticket ticket;
    ImageButton ticketAddLink;
    ListView listOfLinks;
    ImageButton fbButton, ytButton, linkButton;

    int DIALOG_DATE = 1;
    int DIALOG_TIME = 2;
    final Calendar c = Calendar.getInstance();
    int myYear = c.get(Calendar.YEAR);
    int myMonth = c.get(Calendar.MONTH);
    int myDay = c.get(Calendar.DAY_OF_MONTH);
    int myHour = c.get(Calendar.HOUR);
    int myMinute = c.get(Calendar.MINUTE);
    Spinner tagSpinner;
    String ytLink, fbLink, wsLink;
    ArrayList<FBItem> fbList;
    ArrayList<YTItem> ytList;
    ArrayList<LinkItem> linkList;
    Dialog dialog;
    Button buttonDone;
    private int  PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("tickets");

        init();
        setCurrentTime();
        setOnClickLiteners();
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        final ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData("Текст написан", R.drawable.red_tag));
        list.add(new ItemData("Текст проверен", R.drawable.green_tag));
        list.add(new ItemData("Видео собрано", R.drawable.blue_tag));
        list.add(new ItemData("Видео смонтированно", R.drawable.orange_tag));
        list.add(new ItemData("Кэпшн написан", R.drawable.pink_tag));
        list.add(new ItemData("Кэпшн проверен", R.drawable.dark_green_tag));
        list.add(new ItemData("Одобрить с гл.Ред.", R.drawable.black_tag));
        SpinnerAdapter adapter = new SpinnerAdapter(this,
                R.layout.spinner_custom_layout, R.id.tagText, list);
        tagSpinner.setAdapter(adapter);
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                ItemData itemData = list.get(selectedItemPosition);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = format.format(c.getTime());
        ticketDate.setText(strDate);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String strTime = dateFormat.format(c.getTime());
        ticketTime.setText(strTime);
    }

    private void setOnClickLiteners() {
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

        ticketAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(AddTicketActivity.this);
                dialog.setContentView(R.layout.custom_alert_dialog2);
                dialog.setTitle("Add links");
                dialog.setCancelable(true);

                dialogInit(dialog);

                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.hide();
                    }
                });

                fbList = new ArrayList<>();
                ytList = new ArrayList<>();
                linkList = new ArrayList<>();

                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayAdapter adapter;
                        switch (v.getId()){
                            case R.id.fbButton:
                                adapter = new CustomFbAdapter(getApplicationContext(), R.layout.custom_list_of_links, fbList);
                                break;
                            case R.id.ytButton:
                                adapter = new CustomYouTubeAdapter(getApplicationContext(), R.layout.custom_list_of_links, ytList);
                                break;
                            case R.id.linkButton:
                                adapter = new CustomLinksAdapter(getApplicationContext(), R.layout.custom_list_of_links, linkList);
                                break;
                            default:
                                adapter = new CustomFbAdapter(getApplicationContext(), R.layout.custom_list_of_links, fbList);
                                break;
                        }
                        listOfLinks.setAdapter(adapter);
                    }
                };

                fbButton.setOnClickListener(onClickListener);
                ytButton.setOnClickListener(onClickListener);
                linkButton.setOnClickListener(onClickListener);

                dialog.show();

            }


        });

        saveButton.setOnClickListener(new View.OnClickListener() {
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
        });

//        ticketAttachments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                getIntent.setType("image/*");
//                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image/*");
//                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
//                startActivityForResult(chooserIntent, PICK_IMAGE);
//            }
//        });
    }

    private void dialogInit(Dialog dialog) {
        fbButton = (ImageButton) dialog.findViewById(R.id.fbButton);
        ytButton = (ImageButton) dialog.findViewById(R.id.ytButton);
        linkButton = (ImageButton) dialog.findViewById(R.id.linkButton);
        listOfLinks = (ListView) dialog.findViewById(R.id.listOfLinks);

        buttonDone = (Button) dialog.findViewById(R.id.alertDialogDoneButton);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            return new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
        } else if (id == DIALOG_TIME) {
            return new TimePickerDialog(this, myCallTimeBack, myHour, myMinute, true);
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
        ticket = new Ticket();
        ticketDate = (TextView) findViewById(R.id.ticketDateText);
        ticketTime = (TextView) findViewById(R.id.ticketTimeText);
        ticketTitle = (EditText) findViewById(R.id.ticketTitleText);
        //ticketAttachments = (TextView) findViewById(R.id.ticketAttachments);
        ticketCategory = (EditText) findViewById(R.id.ticketCategoryText);
        ticketDescription = (EditText) findViewById(R.id.ticketDescriptionText);
        ticketTaskProfession = (EditText) findViewById(R.id.ticketProfessionText);
        ticketTaskCoWorker = (EditText) findViewById(R.id.ticketCoWorkerText);
        ticketTaskFee = (EditText) findViewById(R.id.ticketTaskFeeText);
        ticketExpenses = (EditText) findViewById(R.id.ticketExpensesNameText);
        ticketSpending = (EditText) findViewById(R.id.ticketSpendingText);
        ticketComment = (EditText) findViewById(R.id.ticketCommentText);
        saveButton = (Button) findViewById(R.id.saveTicketButton);
        ticketAddLink = (ImageButton) findViewById(R.id.ticketAddLink);
        tagSpinner = (Spinner) findViewById(R.id.tagSpinner);
    }

}
