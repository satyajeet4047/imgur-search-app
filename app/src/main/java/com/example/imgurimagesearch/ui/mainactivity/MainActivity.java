package com.example.imgurimagesearch.ui.mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.imgurimagesearch.data.network.networkmodel.ImageDataResponse;
import com.example.imgurimagesearch.R;
import com.example.imgurimagesearch.ui.imageactivity.DetailImageActivity;
import com.example.imgurimagesearch.ui.base.BaseActivity;
import com.example.imgurimagesearch.util.Constants;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/*
     Main launcher screen activity
*/
public class MainActivity extends BaseActivity<MainActivityViewModel> implements  ImagesRecyclerViewAdapter.OnImageClickListener {


    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.searchView) SearchView searchView;

    private Unbinder mBinder;


    ImagesRecyclerViewAdapter adapter;

    final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinder=ButterKnife.bind(this);

        //Initialize view for main activity such as search view
        initView();


        //Observers to update UI components such as recycler view, progressbar, toast
        viewModel.getLoadingStatus().observe(this,new LoadingObserver());
        viewModel.getImages().observe(this, new ImageListObserver());
        viewModel.getResult().observe(this, new ResultObserver());
    }


     @Override
     protected void onDestroy() {
         super.onDestroy();
         mBinder.unbind();
         viewModel.clearSubscription();
     }

     @NonNull
    @Override
    protected MainActivityViewModel createViewModel() {
        MainActivityViewModelFactory factory = new MainActivityViewModelFactory();
        return ViewModelProviders.of(this,factory).get(MainActivityViewModel.class);
    }


    private void initView() {

        adapter = new ImagesRecyclerViewAdapter(MainActivity.this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2 ));
        mRecyclerView.setAdapter(adapter);

        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.SearchImages(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }


    private void clearPreviousData() {
        if(adapter != null)
            adapter.clear();

        searchView.clearFocus();

    }



    // Method invoked when use clicks on grid item
    @Override
    public void onImageClick(ImageDataResponse imageDataResponse) {

        Intent intent = new Intent(this, DetailImageActivity.class);
        intent.putExtra(Constants.IMAGE_DETAILS, imageDataResponse);
        startActivity(intent);
    }


    //Observer to update progressbar status
    private class LoadingObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;

            if (isLoading) {
                clearPreviousData();
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    /*
        Observer to update progressbar status
        This observer updates on jpeg, png images into list
     */

    private class ImageListObserver implements Observer<List<ImageDataResponse>> {

        @Override
        public void onChanged(@Nullable List<ImageDataResponse> imageList) {
            if (imageList == null) return;

            Iterator<ImageDataResponse> iterator = imageList.iterator();
            while(iterator.hasNext()){

                String type =  iterator.next().getType();
                if(type== null){
                    iterator.remove();
                } else if (!type.equals("image/jpeg") && !type.equals("image/png")){
                    iterator.remove();
                }
            }
            Log.i(TAG, "Response Size : " + imageList.size());
            adapter.setList(imageList);

        }
    }


    /*
        Observer to get api operation result status
        It shows generic messages for success, failure, unknown scenarios
     */

    private class ResultObserver implements Observer<Constants.Result> {

        @Override
        public void onChanged(@Nullable Constants.Result result) {

            if (result != null) {
                switch(result){
                    case SUCCESS:
                        showToastMessage(getResources().getString(R.string.search_success_text));
                        break;
                    case FAILURE:
                        showToastMessage(getResources().getString(R.string.search_failure_text));
                        break;
                    case EMPTY_RESULT:
                        showToastMessage(getResources().getString(R.string.no_search_result_text));
                        break;
                    default:
                        showToastMessage(getResources().getString(R.string.search_unknown_text));

                }
            }
        }
    }



    //Method to show toast message

    private void showToastMessage(String string) {
        Toast toast = Toast.makeText(this,string,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }


}
