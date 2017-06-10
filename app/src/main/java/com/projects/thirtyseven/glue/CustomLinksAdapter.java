package com.projects.thirtyseven.glue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

*
 * Created by ThirtySeven on 10.06.2017.



public class CustomLinksAdapter extends BaseAdapter {

    Context context;
    ArrayList<FBItem> fbItems;
    ArrayList<YTItem> ytItems;
    ArrayList<LinkItem> linkItems;
    int whichLink;

    public CustomLinksAdapter(Context context,
                              ArrayList<FBItem> fbItems,
                              ArrayList<YTItem> ytItems,
                              ArrayList<LinkItem> linkItems,
                              int whichLink) {
        switch (whichLink){
            case 1:
                super(context, 0, fbItems);
                break;
            case 2:
                super(context, 0, ytItems);
                break;
            case 3:
                super(context, 0, linkItems);
                break;
        }
        this.context = context;
        this.fbItems = fbItems;
        this.ytItems = ytItems;
        this.linkItems = linkItems;
        this.whichLink = whichLink;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_of_links, parent, false);
        }

        FBItem fbItem = getItem(position);
        YTItem ytItem = getItem(position);
        LinkItem linkItem = getItem(position);
        TextView title = (TextView) listViewItem.findViewById(R.id.linkTitle);

        switch (whichLink){
            case 1:
                title.setText(fbItem.getTitle());
                break;
            case 2:
                title.setText(ytItem.getTitle());
                break;
            case 3:
                title.setText(linkItem.getTitle());
                break;
        }

        return listViewItem;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
