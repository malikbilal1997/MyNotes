package com.malikbilal.notes.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.malikbilal.notes.database.NoteModel;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insertNote(NoteModel noteModel);

    @Query("UPDATE notes SET content=:content, modified=:modified WHERE id=:id")
    void updateNote(String content, String modified, long id);

    @Query("DELETE FROM notes WHERE id=:id")
    void deleteNote(long id);

    @Query("SELECT * FROM notes ORDER BY modified DESC")
    List<NoteModel> getAllNote();

}
