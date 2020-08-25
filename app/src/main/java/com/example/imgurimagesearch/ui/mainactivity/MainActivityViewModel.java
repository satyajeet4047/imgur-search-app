package com.example.imgurimagesearch.ui.mainactivity;

import androidx.lifecycle.MutableLiveData;

import com.example.imgurimagesearch.data.DataManager;
import com.example.imgurimagesearch.data.network.ServiceApiManager;
import com.example.imgurimagesearch.data.network.networkmodel.ImageDataResponse;
import com.example.imgurimagesearch.data.network.networkmodel.SearchResponse;
import com.example.imgurimagesearch.ui.base.BaseViewModel;
import com.example.imgurimagesearch.util.Constants;

import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


 /*
    ViewModel class to communicate with data layer and emit data streams to ui layer
 */

public class MainActivityViewModel extends BaseViewModel {


    private DataManager mDataManager;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<ImageDataResponse>> imageList;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Constants.Result> result;



    MainActivityViewModel(){
        mDataManager = DataManager.getInstance()  ;
        imageList = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        result = new MutableLiveData<>();
    }


    /*
           Method perform network call to search images
           Uses RxJava single observer
     */
    public void SearchImages(String input) {

        setLoading(true);
        ServiceApiManager serviceApiManager = mDataManager.getServiceApiManager();
        DisposableSingleObserver<SearchResponse> observer = getSearchObserver();
        disposable.add(
                serviceApiManager.getImages(input)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer));

    }

    /*
          Returns observer to get emitted result data
     */

    private DisposableSingleObserver<SearchResponse> getSearchObserver() {

        return  new DisposableSingleObserver<SearchResponse>() {

            @Override
            public void onSuccess(SearchResponse searchResponse) {
                if(searchResponse.isSuccess()) {
                    if(searchResponse.getData().isEmpty()){
                        setResult(Constants.Result.EMPTY_RESULT);
                    }else {
                        imageList.postValue(searchResponse.getData());
                    }
                }else {
                    setResult(Constants.Result.FAILURE);
                }
                setLoading(false);

            }
            @Override
            public void onError(Throwable e) {
                setResult(Constants.Result.FAILURE);
                setLoading(false);
            }
        };

    }

    private void setLoading(Boolean loading){
        isLoading.postValue(loading);
    }

    MutableLiveData<List<ImageDataResponse>> getImages() {
        return imageList;
    }

    MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }


    public MutableLiveData<Constants.Result> getResult() {
        return result;
    }

    private void setResult(Constants.Result state) {
        result.postValue(state);
    }

}
