package com.projects.thirtyseven.glue;

import android.content.Context;
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
        Post fbPost = ticket.getFBPost();
        ArrayList<Author> authorArrayList = ticket.getAuthor();

        String getTagText = ticket.getTicketTag();
        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView category = (TextView) listViewItem.findViewById(R.id.tagText);
        TextView description = (TextView) listViewItem.findViewById(R.id.description);
        TextView youtubeViews = (TextView) listViewItem.findViewById(R.id.youtubeViews);
        TextView fbViews = (TextView) listViewItem.findViewById(R.id.fbViews);
        TextView siteViews = (TextView) listViewItem.findViewById(R.id.siteViews);
        TextView date = (TextView) listViewItem.findViewById(R.id.date);
        TextView time = (TextView) listViewItem.findViewById(R.id.time);
        TextView author = (TextView) listViewItem.findViewById(R.id.author);
        ImageView tagView = (ImageView) listViewItem.findViewById(R.id.tagMark);
        // TextView views = (TextView) listViewItem.findViewById(R.id.views);

        author.setText("");
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

        if(fbPost != null){
            fbViews.setText(String.valueOf(fbPost.getReached_unique()));

        }
        //views.setText("12563");

        return listViewItem;
    }

}
