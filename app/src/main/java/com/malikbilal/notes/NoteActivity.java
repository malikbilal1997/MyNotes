package com.malikbilal.notes;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.malikbilal.notes.databinding.ActivityNoteBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.malikbilal.notes.constants.Constants.NOTES_CONTENT;
import static com.malikbilal.notes.constants.Constants.NOTES_ID;
import static com.malikbilal.notes.constants.Constants.NOTES_TITLE;
import static com.malikbilal.notes.MainActivity.database;

public class NoteActivity extends AppCompatActivity {

    private ActivityNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNoteBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        setContentView(view);

        binding.notesActivityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        binding.notesActivityTitle.setText(getIntent().getStringExtra(NOTES_TITLE));

        binding.notesEdit.setText(getIntent().getStringExtra(NOTES_CONTENT));

    }

    @Override
    protected void onPause() {

        super.onPause();

        if (binding.notesEdit.getText() != null) {

            String content = binding.notesEdit.getText().toString();

            if (!content.equals(getIntent().getStringExtra(NOTES_CONTENT))) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                String modified = dateFormat.format(new Date());

                long id = getIntent().getLongExtra(NOTES_ID, 0);

                database.noteDao().updateNote(content, modified, id);

            }

        }

    }
}