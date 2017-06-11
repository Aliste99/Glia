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

public class CustomTicketAdapter extends ArrayAdapter<Ticket> {

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
        Post fbPost;
        ArrayList<Author> authorArrayList = ticket.getAuthor();

        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView tag = (TextView) listViewItem.findViewById(R.id.tagText);
        TextView description = (TextView) listViewItem.findViewById(R.id.description);
        TextView youtubeViews = (TextView) listViewItem.findViewById(R.id.youtubeViews);
        TextView fbViews = (TextView) listViewItem.findViewById(R.id.fbViews);
        TextView siteViews = (TextView) listViewItem.findViewById(R.id.siteViews);
        TextView date = (TextView) listViewItem.findViewById(R.id.date);
        TextView time = (TextView) listViewItem.findViewById(R.id.time);
        TextView author = (TextView) listViewItem.findViewById(R.id.author);

        author.setText("");
        name.setText(ticket.getTicketTitle());
        tag.setText(ticket.getTicketTag());
        description.setText(ticket.getTicketDescription());
        date.setText(ticket.getTicketDate());
        time.setText(ticket.getTicketTime());
        if (authorArrayList != null) {
            int i = 0;
            for (Author a :
                    authorArrayList) {
                if (i != 0)
                    author.append(", " + a.getName());
                else
                    author.append(a.getName());
                i++;
            }
        }
        //views.setText("12563");

        return listViewItem;
    }

}
