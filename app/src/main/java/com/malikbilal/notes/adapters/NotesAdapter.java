package com.malikbilal.notes.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.malikbilal.notes.NoteActivity;
import com.malikbilal.notes.database.NoteModel;
import com.malikbilal.notes.R;
import com.malikbilal.notes.databinding.LayoutItemNoteBinding;

import java.util.List;

import static com.malikbilal.notes.constants.Constants.NOTES_CONTENT;
import static com.malikbilal.notes.constants.Constants.NOTES_ID;
import static com.malikbilal.notes.constants.Constants.NOTES_MODIFIED;
import static com.malikbilal.notes.constants.Constants.NOTES_TITLE;
import static com.malikbilal.notes.MainActivity.database;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private List<NoteModel> arrayList;

    public NotesAdapter(List<NoteModel> arrayList) {

        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new NoteHolder(LayoutItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final NoteHolder holder, final int position) {

        final NoteModel noteModel = arrayList.get(position);

        holder.binding.notesTitle.setText(noteModel.title);

        holder.binding.notesContent.setText(noteModel.content);

        holder.binding.notesDate.setText(noteModel.modified);

        holder.binding.notesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), NoteActivity.class);

                intent.putExtra(NOTES_ID, noteModel.id);

                intent.putExtra(NOTES_TITLE, noteModel.title);

                intent.putExtra(NOTES_CONTENT, noteModel.content);

                intent.putExtra(NOTES_MODIFIED, noteModel.modified);

                v.getContext().startActivity(intent);

            }
        });

        holder.binding.notesOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.binding.notesOption, Gravity.END);

                popupMenu.inflate(R.menu.note_option_menu);

                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.menu_option_delete) {

                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(v.getContext());

                            materialAlertDialogBuilder.setTitle("Do you want to delete?");

                            materialAlertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            });

                            materialAlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    database.noteDao().deleteNote(noteModel.id);

                                    arrayList.remove(position);

                                    notifyItemRemoved(position);

                                }
                            });

                            materialAlertDialogBuilder.setCancelable(false);

                            materialAlertDialogBuilder.show();

                        }

                        return true;
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {

        return arrayList.size();

    }

    static class NoteHolder extends RecyclerView.ViewHolder {

        private LayoutItemNoteBinding binding;

        public NoteHolder(@NonNull LayoutItemNoteBinding binding) {

            super(binding.getRoot());

            this.binding = binding;

        }
    }
}
