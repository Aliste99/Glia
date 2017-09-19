package com.projects.thirtyseven.glue;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
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
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTicketActivity extends AppCompatActivity {
    TextView ticketDate, ticketTime, ticketTag, ticketAttachments;
    EditText ticketCategory, ticketDescription, ticketTaskProfession, ticketTaskCoWorker,
            ticketTaskFee, ticketExpenses, ticketSpending, ticketComment, ticketTitle;
    Button saveButton;
    Button addAuthorButton;
    ImageButton addTagButton;

    FirebaseDatabase database;
    DatabaseReference postsDatabaseReference, webDatabaseReference, ticketDatabaseReference, tagDatabaseReference;
    Ticket ticket;
    ImageButton ticketAddLink;
    GridView authorsGridView;
    ListView listOfLinks;
    ImageButton fbButton, ytButton, linkButton;
    Post fbPost, fbObjToSave;
    YTItem youtubeItem;
    LinkItem linkItem, linkObjToSave;
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
    ArrayList<Post> fbList;
    ArrayList<YTItem> ytList;
    ArrayList<LinkItem> linkList;
    Author author;
    TicketTag tag;
    Dialog dialog;
    Button buttonDone;
    Intent intent;
    private int PICK_IMAGE = 1;
    String id;
    DatabaseReference reference;
    ArrayList<TicketTag> list;
    SpinnerAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        getSupportActionBar().hide();
        list = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        tagDatabaseReference = database.getReference("tags");
        postsDatabaseReference = database.getReference("posts");
        webDatabaseReference = database.getReference("web");
        ticketDatabaseReference = database.getReference("tickets");
        init();
        fbPost = new Post();

        ytList = new ArrayList<>();
        linkList = new ArrayList<>();
        fbList = new ArrayList<>();

        fbList = new ArrayList<>();
        intent = getIntent();
        if (getIntent().getExtras() != null) {

            id = intent.getStringExtra("ticket_id");
            DatabaseReference dbRef = database.getReference("tickets").child(id);
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Ticket t = dataSnapshot.getValue(Ticket.class);
                    ticketTitle.setText(t.getTicketTitle());
                    ticketDate.setText(t.getTicketDate());
                    ticketTime.setText(t.getTicketTime());
                    ticketCategory.setText(t.getTicketCategory());
                    ticketDescription.setText(t.getTicketDescription());
                    ticketTaskProfession.setText(t.getTicketTaskProfession());
                    ticketTaskProfession.setText(t.getTicketTaskProfession());
                    ticketTaskFee.setText(t.getTicketTaskFee());
                    ticketExpenses.setText(t.getTicketExpenses());
                    ticketSpending.setText(t.getTicketSpending());
                    ticketComment.setText(t.getTicketComment());
                    if (t.getFBPost() != null)
                        fbObjToSave = t.getFBPost();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        postsDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fbPost = dataSnapshot.getValue(Post.class);
                fbList.add(fbPost);

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
        webDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                linkItem = dataSnapshot.getValue(LinkItem.class);
                linkList.add(linkItem);
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
        tagDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TicketTag tag = dataSnapshot.getValue(TicketTag.class);
                list.add(tag);
                spinnerAdapter.notifyDataSetChanged();
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

        intent = getIntent();


        setCurrentTime();
        setOnClickLiteners();
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        spinnerAdapter = new SpinnerAdapter(this,
                R.layout.spinner_custom_layout, R.id.tagText, list);
        if (!list.isEmpty())
            tagSpinner.setSelection(0);
        tagSpinner.setAdapter(spinnerAdapter);
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                tag = list.get(selectedItemPosition);

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

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TagCreateDialog tagCreateDialog = new TagCreateDialog();
                tagCreateDialog.setContext(AddTicketActivity.this);
                tagCreateDialog.show(getFragmentManager(), "tag");

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

                fbButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayAdapter adapter;
                        adapter = new CustomFbAdapter(dialog.getContext(), R.layout.custom_list_of_links, fbList);
                        listOfLinks.setAdapter(adapter);
                        listOfLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Post postToConnect = (Post) parent.getItemAtPosition(position);
                                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), postToConnect.getName(), Toast.LENGTH_SHORT).show();
                                if (fbObjToSave != null) {
                                    reference = database.getReference("posts").child(fbObjToSave.getId());
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Post post = dataSnapshot.getValue(Post.class);
                                            post.setConnected(false);
                                            reference.setValue(post);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                fbObjToSave = postToConnect;
                                dialog.hide();
                            }
                        });
                    }
                });
                ytButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        listOfLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                LinkItem linkToConnect = (LinkItem) parent.getItemAtPosition(position);
                                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), linkToConnect.getTitle(), Toast.LENGTH_SHORT).show();
                                if (linkObjToSave != null) {
                                    reference = database.getReference("web").child(linkObjToSave.getId());
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            LinkItem link = dataSnapshot.getValue(LinkItem.class);
                                            link.setConnected(false);
                                            reference.setValue(link);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                linkObjToSave = linkToConnect;
                                dialog.hide();
                            }
                        });

                        dialog.show();
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
                ticket.setTicketTaskProfession(ticketTaskProfession.getText().toString());
                ticket.setTicketTaskProfession(ticketTaskProfession.getText().toString());
                ticket.setTicketTaskFee(ticketTaskFee.getText().toString());
                ticket.setTicketExpenses(ticketExpenses.getText().toString());
                ticket.setTicketSpending(ticketSpending.getText().toString());
                ticket.setTicketComment(ticketComment.getText().toString());
                ticket.setAuthor(authorArrayList);
                if (fbObjToSave != null) {
                    ticket.setFBPost(fbObjToSave);
                    fbObjToSave.setConnected(true);
                    postsDatabaseReference.child(fbObjToSave.getId()).setValue(fbObjToSave);
                }

                if (linkObjToSave != null) {
                    ticket.setWebLink(linkObjToSave);
                    linkObjToSave.setConnected(true);
                    postsDatabaseReference.child(linkObjToSave.getId()).setValue(linkObjToSave);
                }

                if (getIntent().getExtras() == null) {
                    id = ticketDatabaseReference.push().getKey();
                    ticket.setId(id);
                }
                ticket.setId(id);
                ticketDatabaseReference.child(id).setValue(ticket);
                finish();

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
        for (int i = 0; i < names.size(); i++) {
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
        authorsGridView = (GridView) findViewById(R.id.authorsGridView);
        tagSpinner = (Spinner) findViewById(R.id.tagSpinner);
        addTagButton = (ImageButton) findViewById(R.id.addTagButton);
    }

}
