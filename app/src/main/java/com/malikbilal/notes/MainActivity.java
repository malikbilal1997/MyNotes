package com.malikbilal.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.malikbilal.notes.adapters.NotesAdapter;
import com.malikbilal.notes.database.NoteModel;
import com.malikbilal.notes.database.NotesDatabase;
import com.malikbilal.notes.databinding.ActivityMainBinding;
import com.malikbilal.notes.databinding.CreateNoteDialogBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.malikbilal.notes.constants.Constants.DATABASE_FILE;
import static com.malikbilal.notes.constants.Constants.NOTES_CONTENT;
import static com.malikbilal.notes.constants.Constants.NOTES_ID;
import static com.malikbilal.notes.constants.Constants.NOTES_TITLE;

public class MainActivity extends AppCompatActivity {

    public static NotesDatabase database;

    private NotesAdapter notesAdapter;

    private List<NoteModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Initializing Views Binding.

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        setContentView(view);

        // Initializing the Database.

        database = Room.databaseBuilder(this, NotesDatabase.class, DATABASE_FILE).allowMainThreadQueries().build();

        // Initializing Recycler View.

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        notesAdapter = new NotesAdapter(arrayList);

        binding.recyclerView.setAdapter(notesAdapter);

        binding.floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final CreateNoteDialogBinding createNoteDialogBinding = CreateNoteDialogBinding.inflate(getLayoutInflater());

                final MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(v.getContext());

                materialAlertDialogBuilder.setTitle("Create a new note?");

                materialAlertDialogBuilder.setView(createNoteDialogBinding.getRoot());

                materialAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });


                materialAlertDialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (createNoteDialogBinding.titleFieldInput.getText() != null) {

                            String title = createNoteDialogBinding.titleFieldInput.getText().toString().trim();

                            if (!title.isEmpty()) {

                                String content = "";

                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                                String modified = dateFormat.format(new Date());

                                long id = database.noteDao().insertNote(new NoteModel(title, content, modified));

                                Intent intent = new Intent(v.getContext(), NoteActivity.class);

                                intent.putExtra(NOTES_ID, id);

                                intent.putExtra(NOTES_TITLE, title);

                                intent.putExtra(NOTES_CONTENT, content);

                                v.getContext().startActivity(intent);

                            } else {

                                Toast.makeText(v.getContext(), "Give your note a title \uD83D\uDE4F️", Toast.LENGTH_SHORT).show();

                            }


                        }

                    }
                });


                materialAlertDialogBuilder.setCancelable(false);

                materialAlertDialogBuilder.show();

            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();

        fetchAllNotes();

    }

    private void fetchAllNotes() {

        arrayList.clear();

        arrayList.addAll(database.noteDao().getAllNote());

        notesAdapter.notifyDataSetChanged();

    }


}