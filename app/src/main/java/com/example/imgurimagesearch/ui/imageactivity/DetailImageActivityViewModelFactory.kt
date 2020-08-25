package com.example.imgurimagesearch.ui.imageactivity


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


 class DetailImageActivityViewModelFactory(private val application: Application): ViewModelProvider.Factory {



    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailImageActivityViewModel::class.java)) {
            return DetailImageActivityViewModel(application ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }


}