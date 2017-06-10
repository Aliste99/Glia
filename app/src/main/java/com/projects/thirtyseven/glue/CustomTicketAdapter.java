package com.projects.thirtyseven.glue;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomTicketAdapter extends ArrayAdapter<Ticket>{

    private Context context;

    public CustomTicketAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList ticket) {
        super(context, 0, ticket);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }

        Ticket ticket = getItem(position);

        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView tag = (TextView) listViewItem.findViewById(R.id.tagText);
        TextView description = (TextView) listViewItem.findViewById(R.id.description);
       // TextView views = (TextView) listViewItem.findViewById(R.id.views);

        name.setText(ticket.getTicketTitle());
        tag.setText(ticket.getTicketTag());
        description.setText(ticket.getTicketDescription());
        //views.setText("12563");

        return listViewItem;
    }

}
