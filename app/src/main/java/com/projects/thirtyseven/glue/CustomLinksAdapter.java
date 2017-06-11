package com.projects.thirtyseven.glue;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomLinksAdapter extends ArrayAdapter<LinkItem> {

    Context context;

    public CustomLinksAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<LinkItem> link) {
        super(context, 0, link);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null)
            listViewItem = LayoutInflater.from(context).inflate(R.layout.custom_list_of_links, parent, false);

        LinkItem linkItem = (LinkItem) getItem(position);
        TextView title = (TextView) listViewItem.findViewById(R.id.linkTitle);

        if (linkItem != null)
            title.setText(linkItem.getTitle());

        return listViewItem;
    }
}
