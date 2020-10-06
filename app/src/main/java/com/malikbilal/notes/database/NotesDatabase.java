package com.malikbilal.notes.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteModel.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

}
