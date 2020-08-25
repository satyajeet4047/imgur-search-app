package com.example.imgurimagesearch.ui.mainactivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


 /*
    Class provides viewmodel for main activity

 */
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
