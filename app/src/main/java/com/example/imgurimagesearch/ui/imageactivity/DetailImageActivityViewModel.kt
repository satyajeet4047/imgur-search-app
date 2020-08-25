package com.example.imgurimagesearch.ui.imageactivity


import android.app.Application
import androidx.lifecycle.LiveData
import com.example.imgurimagesearch.data.DataManager
import com.example.imgurimagesearch.data.localdatabase.CommentsDatabaseRepository
import com.example.imgurimagesearch.data.localdatabase.Entity.CommentsEntity
import com.example.imgurimagesearch.ui.base.BaseViewModel

/*
    ViewModel class to perform operations to add comments into the database and retrieve respective comment
    from database using unique image id
 */
class DetailImageActivityViewModel(application: Application): BaseViewModel() {

    var dbManager : CommentsDatabaseRepository? = null


    init {
        val dataManager = DataManager.getInstance()
        dbManager = dataManager.getLocalDatabase(application)
    }


     fun addComment(id : String , comment : String, lastComment : String) {

        val commentsEntity = CommentsEntity()
        commentsEntity.imageId = id
        commentsEntity.comment = comment

        if(lastComment != "No comments"){
            dbManager?.updateComment(commentsEntity)
        } else {
            dbManager?.addComment(commentsEntity)
        }

    }



     fun fetchComment(id : String) : LiveData<CommentsEntity>? {
        return dbManager?.getComment(id)
    }
}