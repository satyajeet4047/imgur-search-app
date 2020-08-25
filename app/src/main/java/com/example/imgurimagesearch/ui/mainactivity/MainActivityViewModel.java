package com.example.imgurimagesearch.ui.mainactivity;

import androidx.lifecycle.MutableLiveData;

import com.example.imgurimagesearch.data.DataManager;
import com.example.imgurimagesearch.data.network.ServiceApiManager;
import com.example.imgurimagesearch.data.network.networkmodel.ImageDataResponse;
import com.example.imgurimagesearch.data.network.networkmodel.SearchResponse;
import com.example.imgurimagesearch.ui.base.BaseViewModel;
import com.example.imgurimagesearch.util.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


 /*
    ViewModel class to communicate with data layer and emit data streams to ui layer
 */

public class MainActivityViewModel extends BaseViewModel {


    private DataManager mDataManager;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<ImageDataResponse>> imageList;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Constants.Result> result;

    private PublishSubject<String> publisher = PublishSubject.create();



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
        final ServiceApiManager serviceApiManager = mDataManager.getServiceApiManager();
        DisposableObserver<SearchResponse> observer = getSearchObserver();
        disposable.add(
                publisher.debounce(250, TimeUnit.MILLISECONDS)
                        .switchMapSingle(new Function<String, SingleSource<SearchResponse>>() {
                    @Override
                    public SingleSource<SearchResponse> apply(String s) throws Exception {
                        return serviceApiManager.getImages(s)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }

                })
                .subscribeWith(observer));
        publisher.onNext(input);
    }

    /*
          Returns observer to get emitted result data
     */

    private DisposableObserver<SearchResponse> getSearchObserver() {

        return  new DisposableObserver<SearchResponse>() {

            @Override
            public void onNext(SearchResponse searchResponse) {

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

            @Override
            public void onComplete() {

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

    public void clearSubscription(){
        disposable.clear();
    }

}
