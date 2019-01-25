package com.leothosthoren.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.leothosthoren.realestatemanager.database.dao.UserDao;
import com.leothosthoren.realestatemanager.entities.User;

public class UserDataRepository {

    private final UserDao userDao;

    public UserDataRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    // --- GET USER ---
    public LiveData<User> getUser(long userId) {
        return this.userDao.getUser(userId);
    }
}