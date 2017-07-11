package com.projects.thirtyseven.glue;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by ThirtySeven on 11.07.2017.
 */

public class TicketInfoDialog extends DialogFragment implements View.OnClickListener {

    TextView shared, likes, wow, haha, reached_total, reached_unique, loves, sad, angry;
    Post currentPost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Инфориация");
        View view = inflater.inflate(R.layout.ticket_info_dialog, null);
        shared = (TextView) view.findViewById(R.id.shared);
        likes = (TextView) view.findViewById(R.id.likes_count);
        wow = (TextView) view.findViewById(R.id.wow_count);
        haha = (TextView) view.findViewById(R.id.haha_count);
        reached_total = (TextView) view.findViewById(R.id.reached_total);
        reached_unique = (TextView) view.findViewById(R.id.reached_unique);
        loves = (TextView) view.findViewById(R.id.loves_count);
        sad = (TextView) view.findViewById(R.id.sad_count);
        angry = (TextView) view.findViewById(R.id.angry_count);
        try {
            shared.setText(String.valueOf(currentPost.getSharesCount()));
            likes.setText(String.valueOf(currentPost.getLikesCount()));
            wow.setText(String.valueOf(currentPost.getWowCount()));
            haha.setText(String.valueOf(currentPost.getHahaCount()));
            reached_total.setText(String.valueOf(currentPost.getReached_total()));
            reached_unique.setText(String.valueOf(currentPost.getReached_unique()));
            loves.setText(String.valueOf(currentPost.getLovesCount()));
            sad.setText(String.valueOf(currentPost.getSadCount()));
            angry.setText(String.valueOf(currentPost.getAngryCount()));
        } catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onClick(View v) {

        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


}
