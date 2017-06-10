package com.projects.thirtyseven.glue;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexwalker on 10.06.17.
 */

class CustomAddAuthorAdapter extends ArrayAdapter {
    public CustomAddAuthorAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList <Author> author) {
        super(context, 0, author);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View gridViewItem = convertView;
        if (gridViewItem == null) {
            gridViewItem = LayoutInflater.from(getContext()).inflate(R.layout.custom_gridview_item, parent, false);
        }

        Author currentAuthor = (Author) getItem(position);

        ImageView photo = (ImageView) gridViewItem.findViewById(R.id.authorPhotoImageView);
        TextView name = (TextView) gridViewItem.findViewById(R.id.authorNameTextView);


        name.setText(currentAuthor.getName());
        photo.setImageDrawable(currentAuthor.getPhoto());

        return gridViewItem;
    }
}
