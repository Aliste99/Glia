package com.projects.thirtyseven.glue;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class AddAuthorActivity extends AppCompatActivity {

    GridView gridView;
    CustomAddAuthorAdapter customAddAuthorAdapter;
    ArrayList<Author> authorArrayList;
    Author authorRinat;
    Author authorAnna;
    Author authorAzat;
    ArrayList namesArrayList;
    ArrayList photosArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        gridView = (GridView)findViewById(R.id.authorsGridView);

        authorRinat = new Author("Rinat", R.drawable.rinat);
        authorAnna = new Author("Anna", R.drawable.anna);
        authorAzat = new Author("Azat", R.drawable.azat);
        namesArrayList = new ArrayList();
        photosArrayList = new ArrayList();

        authorArrayList = new ArrayList<>();
        authorArrayList.add(authorRinat);
        authorArrayList.add(authorAnna);
        authorArrayList.add(authorAzat);
        customAddAuthorAdapter = new CustomAddAuthorAdapter(this, R.layout.custom_gridview_item, authorArrayList);
        gridView.setAdapter(customAddAuthorAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Author selectedAuthor;
                selectedAuthor = authorArrayList.get(position);
                select(view, selectedAuthor, position);
                Log.v("Authors", "selected authors: " + namesArrayList.toString());
                Intent result = new Intent();
                result.putStringArrayListExtra("name", namesArrayList);
                result.putIntegerArrayListExtra("photo", photosArrayList);
                setResult(RESULT_OK, result);
            }
        });

    }

    private void select(View view, Author selectedAuthor, int position) {
        if(!view.isActivated()){
            view.setBackgroundColor(Color.GREEN);
            namesArrayList.add(selectedAuthor.getName());
            photosArrayList.add(selectedAuthor.getPhoto());
            view.setActivated(true);
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
            namesArrayList.remove(position);
            photosArrayList.remove(position);
            view.setActivated(false);
        }
    }

}
