package com.example.imgurimagesearch.data;

import android.app.Application;


import com.example.imgurimagesearch.data.localdatabase.CommentsDatabaseRepository;
import com.example.imgurimagesearch.data.network.ApiClient;
import com.example.imgurimagesearch.data.network.ServiceApiManager;


/*
 Data Manger class provides references to local database and remote api service
 */
public class DataManager {

    private static DataManager sInstance;

    private DataManager() {
        // This class is not publicly instantiable
    }

    public static synchronized DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }


    public ServiceApiManager getServiceApiManager(){
        return ApiClient.getNetworkService();

    }

    public CommentsDatabaseRepository getLocalDatabase(Application application){
        return CommentsDatabaseRepository.getDatabase(application.getBaseContext());

    }

}
