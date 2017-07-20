package com.projects.thirtyseven.glue;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ThirtySeven on 10-Jun-17.
 */

public class SpinnerAdapter extends ArrayAdapter<TicketTag> {
    int groupid;
    Activity context;
    ArrayList<TicketTag> list;
    LayoutInflater inflater;

    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<TicketTag>
            list) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.tagColor);
        TextView textView = (TextView) itemView.findViewById(R.id.tagText);

        ImageView tagView = (ImageView) itemView.findViewById(R.id.tagColor);
        tagView.setImageResource(R.drawable.spinner_tag);
        imageView.setColorFilter(list.get(position).getColor());
        textView.setText(list.get(position).getTitle());

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {
        return getView(position, convertView, parent);

    }
}
