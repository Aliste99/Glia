package com.projects.thirtyseven.glue;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TagCreateDialog extends DialogFragment implements View.OnClickListener {

    ColorPickerView colorPickerView;
    ColorPickerClickListener colorPickerClickListener;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Context context;
    EditText title;
    Button create;
    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle("Создание тэга");
        View view = inflater.inflate(R.layout.tag_create_dialog, null);
        init(view);

        create.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        colorPickerView = (ColorPickerView) view.findViewById(R.id.color_picker_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tags");
        create = (Button) view.findViewById(R.id.createTagButton);
        title = (EditText) view.findViewById(R.id.titleEditText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createTagButton:
                if (!title.getText().toString().equals("")) {
                    TicketTag tag = new TicketTag( );
                    tag.setColor(colorPickerView.getSelectedColor());
                    tag.setTitle(title.getText().toString());
                    id = databaseReference.push().getKey();
                    tag.setId(id);
                    databaseReference.child(id).setValue(tag);
                    Toast.makeText(context, "Готово!", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else
                    Toast.makeText(context, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancelTagButton:
                dismiss();
                break;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
