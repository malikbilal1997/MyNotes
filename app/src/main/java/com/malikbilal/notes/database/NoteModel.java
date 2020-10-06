package com.malikbilal.notes.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteModel {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "modified")
    public String modified;

    public NoteModel(String title, String content, String modified) {

        this.title = title;

        this.content = content;

        this.modified = modified;

    }
}
