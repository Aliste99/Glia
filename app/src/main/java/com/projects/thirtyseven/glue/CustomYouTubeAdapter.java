package com.projects.thirtyseven.glue;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ThirtySeven on 10.06.2017.
 */

public class CustomYouTubeAdapter extends ArrayAdapter<YTItem> {

    Context context;

    public CustomYouTubeAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<YTItem> youTube) {
        super(context, 0, youTube);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null)
            listViewItem = LayoutInflater.from(context).inflate(R.layout.custom_list_of_links, parent, false);

        YTItem ytItem = getItem(position);
        TextView title = (TextView) listViewItem.findViewById(R.id.linkTitle);

        if (ytItem != null)
            title.setText(ytItem.getTitle());

        return listViewItem;
    }
}