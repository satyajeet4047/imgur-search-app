package com.example.imgurimagesearch.data.localdatabase.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*
    Serializable Entity class having database columns
 */

@Entity
public class CommentsEntity implements Serializable {


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "imageId")
    private String imageId;

    @ColumnInfo(name = "comment")
    private String comment;


   public CommentsEntity(){

    }
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
