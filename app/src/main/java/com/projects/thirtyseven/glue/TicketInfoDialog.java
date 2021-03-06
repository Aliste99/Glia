package com.projects.thirtyseven.glue;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketInfoDialog extends DialogFragment implements View.OnClickListener {

    private static String ticket_id;
    final String LOG_TAG = "info";
    Context context;

    TextView shared, likes, wow, haha, reached_total, reached_unique, loves, sad, angry, title;
    Button change, delete;
    Post currentPost;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference reference;
    String postId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle("Информация");
        View view = inflater.inflate(R.layout.ticket_info_dialog, null);
        init(view);

        if (ticket_id != null) {
            databaseReference = firebaseDatabase.getReference("tickets").child(ticket_id).child("fbpost");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentPost = dataSnapshot.getValue(Post.class);
                    try {
                        title.setText(String.valueOf(currentPost.getName()));
                        shared.setText(String.valueOf(currentPost.getSharesCount()));
                        likes.setText(String.valueOf(currentPost.getLikesCount()));
                        wow.setText(String.valueOf(currentPost.getWowCount()));
                        haha.setText(String.valueOf(currentPost.getHahaCount()));
                        reached_total.setText(String.valueOf(currentPost.getReached_total()));
                        reached_unique.setText(String.valueOf(currentPost.getReached_unique()));
                        loves.setText(String.valueOf(currentPost.getLovesCount()));
                        sad.setText(String.valueOf(currentPost.getSadCount()));
                        angry.setText(String.valueOf(currentPost.getAngryCount()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        change.setOnClickListener(this);
        delete.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        title = (TextView) view.findViewById(R.id.title);
        shared = (TextView) view.findViewById(R.id.shared);
        likes = (TextView) view.findViewById(R.id.likes_count);
        wow = (TextView) view.findViewById(R.id.wow_count);
        haha = (TextView) view.findViewById(R.id.haha_count);
        reached_total = (TextView) view.findViewById(R.id.reached_total);
        reached_unique = (TextView) view.findViewById(R.id.reached_unique);
        loves = (TextView) view.findViewById(R.id.loves_count);
        sad = (TextView) view.findViewById(R.id.sad_count);
        angry = (TextView) view.findViewById(R.id.angry_count);
        firebaseDatabase = FirebaseDatabase.getInstance();
        change = (Button) view.findViewById(R.id.changeButton);
        delete = (Button) view.findViewById(R.id.deleteButton);
    }

    public static TicketInfoDialog addSomeString(String temp) {
        TicketInfoDialog f = new TicketInfoDialog();
        ticket_id = temp;
        return f;
    }

    void setString(String ticket_id) {
        TicketInfoDialog.ticket_id = ticket_id;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeButton:
                Intent intent = new Intent(context, AddTicketActivity.class);
                intent.putExtra("ticket_id", ticket_id);
                startActivity(intent);
                dismiss();
                break;
            case R.id.deleteButton:
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Вы уверены?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference dReference = firebaseDatabase.getReference("tickets").child(ticket_id);
                        dReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Ticket ticket = dataSnapshot.getValue(Ticket.class);
                                if (ticket.getFBPost() != null) {
                                    Post post = ticket.getFBPost();
                                    postId = post.getId();
                                    reference = firebaseDatabase.getReference("posts").child(postId);
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
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        dReference.removeValue();
                        dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                alertDialog.show();
                break;
            default:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
