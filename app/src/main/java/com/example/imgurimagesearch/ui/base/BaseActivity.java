package com.example.imgurimagesearch.ui.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {

    protected VM viewModel;


    @NonNull
    protected abstract VM createViewModel();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
    }
}

