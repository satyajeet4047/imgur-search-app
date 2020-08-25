package com.example.imgurimagesearch.data.localdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.imgurimagesearch.data.localdatabase.Dao.ICommentsDao;
import com.example.imgurimagesearch.data.localdatabase.Entity.CommentsEntity;

@Database(entities = {CommentsEntity.class},version = 1,exportSchema = false)
public abstract class CommentsDatabase extends RoomDatabase {

    public abstract ICommentsDao getCommentsDao();
}
