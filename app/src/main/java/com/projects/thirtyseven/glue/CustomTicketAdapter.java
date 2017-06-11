package com.projects.thirtyseven.glue;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

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
        String getTagText = ticket.getTicketTag();
        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView category = (TextView) listViewItem.findViewById(R.id.tagText);
        TextView description = (TextView) listViewItem.findViewById(R.id.description);
        ImageView tagView = (ImageView) listViewItem.findViewById(R.id.tagMark);
        // TextView views = (TextView) listViewItem.findViewById(R.id.views);

        name.setText(ticket.getTicketTitle());
        category.setText(getTagText);

        tagView.setImageResource(R.drawable.bookmark);

        if (getTagText.equalsIgnoreCase(context.getString(R.string.text_wrote)))
            tagView.setColorFilter(ContextCompat.getColor(context, R.color.redColor));
        else if (getTagText.equalsIgnoreCase(context.getString(R.string.text_checked)))
            tagView.setColorFilter(ContextCompat.getColor(context, R.color.greenColor));
        else if (getTagText.equalsIgnoreCase(context.getString(R.string.video_collected)))
            tagView.setColorFilter(ContextCompat.getColor(context, R.color.blueColor));
        else if (getTagText.equalsIgnoreCase(context.getString(R.string.video_mounted)))
            tagView.setColorFilter(ContextCompat.getColor(context, R.color.orangeColor));
        else if (getTagText.equalsIgnoreCase(context.getString(R.string.caption_wrote)))
            tagView.setColorFilter(ContextCompat.getColor(context, R.color.pinkColor));
        else if (getTagText.equalsIgnoreCase(context.getString(R.string.caption_checked)))
            tagView.setColorFilter(ContextCompat.getColor(context, R.color.darkGreenColor));
        else if (getTagText.equalsIgnoreCase(context.getString(R.string.approval_with_the_editor)))
            tagView.setColorFilter(ContextCompat.getColor(context, R.color.blackColor));
        else tagView.setColorFilter(ContextCompat.getColor(context, R.color.greyColorLight));

        description.setText(ticket.getTicketDescription());
        //views.setText("12563");

        return listViewItem;
    }

}
