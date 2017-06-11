package com.projects.thirtyseven.glue;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class CustomFbAdapter extends ArrayAdapter<Post> {

    Context context;



    public CustomFbAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Post> fb) {
        super(context, 0, fb);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null)
            listViewItem = LayoutInflater.from(context).inflate(R.layout.custom_list_of_links, parent, false);

        Post fbPost = getItem(position);
        TextView title = (TextView) listViewItem.findViewById(R.id.linkTitle);


        title.setText(fbPost.getName());

        return listViewItem;
    }
}