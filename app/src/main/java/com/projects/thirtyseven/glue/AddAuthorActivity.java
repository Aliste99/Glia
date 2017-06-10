package com.projects.thirtyseven.glue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class AddAuthorActivity extends AppCompatActivity {

    GridView gridView;
    CustomAddAuthorAdapter customAddAuthorAdapter;
    ArrayList<Author> authorArrayList;
    Author authorRinat;
    Author authorAnna;
    Author authorAzat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        gridView = (GridView)findViewById(R.id.authorsGridView);

        authorRinat = new Author("Rinat", R.drawable.rinat);
        authorAnna = new Author("Anna", R.drawable.anna);
        authorAzat = new Author("Azat", R.drawable.azat);

        authorArrayList = new ArrayList<>();
        authorArrayList.add(authorRinat);
        authorArrayList.add(authorAnna);
        authorArrayList.add(authorAzat);
        customAddAuthorAdapter = new CustomAddAuthorAdapter(this, R.layout.custom_gridview_item, authorArrayList);
        gridView.setAdapter(customAddAuthorAdapter);

    }
}
