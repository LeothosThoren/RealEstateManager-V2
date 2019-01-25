package com.leothosthoren.realestatemanager.injections;

import android.content.Context;

import com.leothosthoren.realestatemanager.database.RealEstateDatabase;
import com.leothosthoren.realestatemanager.repositories.RealEstateDataRepository;
import com.leothosthoren.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static RealEstateDataRepository provideRealEstateDataSource(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new RealEstateDataRepository(database.realEstateDao());
    }

    public static UserDataRepository provideUserDataSource(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new UserDataRepository(database.userDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        RealEstateDataRepository dataSourceRealEstate = provideRealEstateDataSource(context);
        UserDataRepository dataSourceUser = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceRealEstate, dataSourceUser, executor);
    }
}
