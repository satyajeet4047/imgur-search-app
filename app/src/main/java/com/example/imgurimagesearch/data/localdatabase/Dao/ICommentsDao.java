package com.example.imgurimagesearch.data.localdatabase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.imgurimagesearch.data.localdatabase.Entity.CommentsEntity;

/*
    Dao layer class defines operational methods
 */
@Dao
public interface ICommentsDao {


    @Insert
    void addComment(CommentsEntity comment);


    @Query("SELECT * FROM CommentsEntity WHERE imageId =:imageId")
    LiveData<CommentsEntity> getCommentById(String imageId);

    @Update
    void update(CommentsEntity commentsEntity);
}
