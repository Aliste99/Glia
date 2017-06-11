package com.projects.thirtyseven.glue;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    Button addAuthorButton;

    FirebaseDatabase database;
    DatabaseReference databaseReference, fbRef, youTubeRef, linkRef;
    Ticket ticket;
    ImageButton ticketAddLink;
    GridView authorsGridView;
    ListView listOfLinks;
    ImageButton fbButton, ytButton, linkButton;
    FBItem fbItem;
    YTItem youtubeItem;
    LinkItem linkItem;
    View.OnClickListener onClickListener;

    int DIALOG_DATE = 1;
    int DIALOG_TIME = 2;
    final Calendar c = Calendar.getInstance();
    int myYear = c.get(Calendar.YEAR);
    int myMonth = c.get(Calendar.MONTH);
    int myDay = c.get(Calendar.DAY_OF_MONTH);
    int myHour = c.get(Calendar.HOUR);
    int myMinute = c.get(Calendar.MINUTE);
    ArrayList<Author> authorArrayList;
    CustomAddAuthorAdapter authorAdapter;
    Spinner tagSpinner;
    String ytLink, fbLink, wsLink;
    ArrayList<FBItem> fbList;
    ArrayList<YTItem> ytList;
    ArrayList<LinkItem> linkList;
    String tag;
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
        fbRef = database.getReference("facebook");
        youTubeRef = database.getReference("youtube");
        linkRef = database.getReference("link");

        fbItem = new FBItem();

       /* fbItem.setTitle("Демонстранты из Нарына извинились перед студентами и преподавателями УЦА");
        fbItem.setText("Группа жителей Нарына извинилась перед преподавателями Университета Центральной Азии. Днем ранее они заставили студентов вуза на коленях просить прощения за драку на соревновании по баскетболу.\n" +
                "\n" +
                "Жители Нарына снова пришли в УЦА, но на этот раз сами попросили прощения у его преподавателей. Молодые люди заявили, что конфликт произошел из-за «недоразумения и недопонимания».\n" +
                "\n" +
                "Молодежные активисты города Нарын подарили преподавателям подарки.\n" +
                "\n" +
                "«Двум сотрудникам университета, в том числе преподавателю, который вчера попросил извинения за своих студентов, представители местной молодежи надели национальный головной убор “ак-калпак” и подарили две книги эпоса “Манас” в знак уважения», — сообщает пресс-служба МВД.\n" +
                "\n" +
                "Руководство вуза и его преподаватели считают, что инцидент исчерпан, и надеются, что и дальше смогут мирно взаимодействовать с местными жителями.\n" +
                "\n" +
                "На встрече местных жителей и студентов присутствовали руководство УВД, полпред Нарынской области и деканы университета.\n" +
                "\n" +
                "Ранее МВД возбудило уголовное дело по статье «Хулиганство» против местных жителей, вынудивших студентов и преподавателей просить прощения на коленях. Их обвиняют в нарушении общественного порядка.");
        fbItem.setURL("https://kloop.kg/blog/2017/06/09/ak-kalpak-i-manas-demonstranty-iz-naryna-izvinilis-pered-studentami-i-prepodavatelyami-utsa/");
        fbItem.setReachedTotal("1564");
        fbItem.setReachedUnique("1137");
        fbItem.setShares("13");

        fbRef.push().setValue(fbItem);*/
        fbList = new ArrayList<>();


        fbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fbItem = dataSnapshot.getValue(FBItem.class);
                fbList.add(fbItem);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        init();
        setCurrentTime();
        setOnClickLiteners();
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        final ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData(getString(R.string.text_wrote), R.drawable.red_tag));
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
                tag = itemData.getText();
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

                ytList = new ArrayList<>();
                linkList = new ArrayList<>();

                fbButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AddTicketActivity.this, "fb", Toast.LENGTH_SHORT).show();
                        ArrayAdapter adapter;
                        adapter = new CustomFbAdapter(dialog.getContext(), R.layout.custom_list_of_links, fbList);
                        listOfLinks.setAdapter(adapter);
                    }
                });
                ytButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AddTicketActivity.this, "yt", Toast.LENGTH_SHORT).show();
                        ArrayAdapter adapter;
                        adapter = new CustomYouTubeAdapter(dialog.getContext(), R.layout.custom_list_of_links, ytList);
                        listOfLinks.setAdapter(adapter);
                    }
                });
                linkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayAdapter adapter;
                        adapter = new CustomLinksAdapter(dialog.getContext(), R.layout.custom_list_of_links, linkList);
                        listOfLinks.setAdapter(adapter);
                    }
                });

                dialog.show();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticket.setTicketTitle(ticketTitle.getText().toString());
                ticket.setTicketDate(ticketDate.getText().toString());
                ticket.setTicketTime(ticketTime.getText().toString());
                ticket.setTicketTag(tag);
                ticket.setTicketCategory(ticketCategory.getText().toString());
                ticket.setTicketDescription(ticketDescription.getText().toString());
<<<<<<< HEAD
                ticket.setTicketTaskProfession(ticketTaskProfession.getText     ().toString());
                ticket.setTicketTaskCoWorker(ticketTaskCoWorker.getText().toString());
=======
                ticket.setTicketTaskProfession(ticketTaskProfession.getText().toString());
>>>>>>> 9616420b6c324b0c84d8bbe7a7f66509d140d7fc
                ticket.setTicketTaskFee(ticketTaskFee.getText().toString());
                ticket.setTicketExpenses(ticketExpenses.getText().toString());
                ticket.setTicketSpending(ticketSpending.getText().toString());
                ticket.setTicketComment(ticketComment.getText().toString());
                databaseReference.push().setValue(ticket);
            }
        });
        addAuthorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTicketActivity.this, AddAuthorActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        authorsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                authorArrayList.remove(position);
                authorAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        ArrayList<String> names = data.getStringArrayListExtra("name");
        ArrayList<Integer> photos = data.getIntegerArrayListExtra("photo");
        Log.v("Authors", "authors to show: " + names.toString());
        Log.v("Authors", "photos to show: " + photos.toString());
        authorArrayList = new ArrayList<>();
        Author selectedAuthor;
        for (int i = 0; i < names.size(); i ++){
            selectedAuthor = new Author(names.get(i), photos.get(i));
            authorArrayList.add(selectedAuthor);
            Log.v("Authors", "selected author: " + selectedAuthor.toString());
        }
        Log.v("Authors", "authors: " + authorArrayList.toString());
        authorAdapter = new CustomAddAuthorAdapter(this, R.layout.authors_listview_item, authorArrayList);
        authorsGridView.setAdapter(authorAdapter);

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
        ticketTaskFee = (EditText) findViewById(R.id.ticketTaskFeeText);
        ticketExpenses = (EditText) findViewById(R.id.ticketExpensesNameText);
        ticketSpending = (EditText) findViewById(R.id.ticketSpendingText);
        ticketComment = (EditText) findViewById(R.id.ticketCommentText);
        saveButton = (Button) findViewById(R.id.saveTicketButton);
        ticketAddLink = (ImageButton) findViewById(R.id.ticketAddLink);
        addAuthorButton = (Button) findViewById(R.id.addAuthorButton);
        authorsGridView = (GridView)findViewById(R.id.authorsGridView);
        tagSpinner = (Spinner) findViewById(R.id.tagSpinner);
    }

}
