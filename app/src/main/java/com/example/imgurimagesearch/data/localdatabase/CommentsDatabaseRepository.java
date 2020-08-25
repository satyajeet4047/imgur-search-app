package com.example.imgurimagesearch.data.localdatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.imgurimagesearch.data.localdatabase.Entity.CommentsEntity;

import java.util.concurrent.ExecutionException;


/*
    Singleton Database operator class
    Creates new database, performs queries on database


 */
public class CommentsDatabaseRepository {

    private final String db_Name = "CommentsDb";
    private CommentsDatabase mCommentsDatabase ;
    private static CommentsDatabaseRepository mCommentsDatabaseRepository;

    private CommentsDatabaseRepository(Context context) {

        mCommentsDatabase = Room.databaseBuilder(context,CommentsDatabase.class, db_Name).build();
    }


    public static CommentsDatabaseRepository getDatabase(Context context){

        if(mCommentsDatabaseRepository == null){
            mCommentsDatabaseRepository = new CommentsDatabaseRepository(context);
        }
        return mCommentsDatabaseRepository;
    }

    @SuppressLint("StaticFieldLeak")
    public boolean addComment(final CommentsEntity commentsEntity) {

        try {
            return new AsyncTask<Void,Void,Boolean>(){
                @Override
                protected Boolean doInBackground(Void... voids) {
                    mCommentsDatabase.getCommentsDao().addComment(commentsEntity);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    public boolean updateComment(final CommentsEntity commentsEntity) {

        try {
            return new AsyncTask<Void,Void,Boolean>(){
                @Override
                protected Boolean doInBackground(Void... voids) {
                    mCommentsDatabase.getCommentsDao().update(commentsEntity);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    public LiveData<CommentsEntity> getComment(final String id) {

        LiveData<CommentsEntity> commentsEntityLiveData = null;
        try {
            commentsEntityLiveData = new AsyncTask<Void,Void, LiveData<CommentsEntity>>(){
                @Override
                protected LiveData<CommentsEntity> doInBackground(Void... voids) {
                    return mCommentsDatabase.getCommentsDao().getCommentById(id);
                }

                @Override
                protected void onPostExecute(LiveData<CommentsEntity> item) {
                    super.onPostExecute(item);
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return commentsEntityLiveData;
    }
}
