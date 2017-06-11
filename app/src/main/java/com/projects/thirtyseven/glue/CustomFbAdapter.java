package com.projects.thirtyseven.glue;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.AccessControlContext;
import java.sql.Time;
import java.util.ArrayList;

public class CustomFbAdapter extends ArrayAdapter<Post> {

    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Post fbPost;
    TextView title;
    ArrayList<Post> postArrayList;
    int postPosition;

    public CustomFbAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Post> fb) {
        super(context, 0, fb);
        this.context = context;
        postArrayList = fb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        this.postPosition = position;
        if (listViewItem == null)
            listViewItem = LayoutInflater.from(context).inflate(R.layout.custom_list_of_links, parent, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("posts");

        fbPost = getItem(position);
        try {
            if(fbPost.isConnected()){
                postArrayList.remove(fbPost);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        title = (TextView) listViewItem.findViewById(R.id.linkTitle);
        title.setText(fbPost.getName());

        return listViewItem;
    }
}