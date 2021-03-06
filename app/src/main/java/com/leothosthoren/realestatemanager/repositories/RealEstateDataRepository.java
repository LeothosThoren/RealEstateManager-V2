package com.leothosthoren.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.leothosthoren.realestatemanager.database.dao.RealEstateDao;
import com.leothosthoren.realestatemanager.entities.RealEstate;

import java.util.List;

public class RealEstateDataRepository {

    private final RealEstateDao realEstateDao;

    public RealEstateDataRepository(RealEstateDao realEstateDao) {
        this.realEstateDao = realEstateDao;
    }

    // --- GET ---

    public LiveData<List<RealEstate>> getRealEstate(long userId) {
        return this.realEstateDao.getRealEstate(userId);
    }

    // --- SEARCH ---

    public LiveData<List<RealEstate>> searchRealEstate(String type, String area, Integer minSurface, Integer maxSurface, Long minPrice, Long maxPrice,
                                                       Integer minRoom, Integer maxRoom, long userId) {
        return this.realEstateDao.searchRealEstate(type, area, minSurface, maxSurface, minPrice, maxPrice,
                minRoom, maxRoom, userId);
    }

    // --- CREATE ---

    public void createRealEstate(RealEstate realEstate) {
        realEstateDao.insertRealEstate(realEstate);
    }

    // --- DELETE ---
    public void deleteRealEstate(long realEstateId) {
        realEstateDao.deleteRealEstate(realEstateId);
    }

    // --- UPDATE ---
    public void updateRealEstate(RealEstate realEstate) {
        realEstateDao.updateRealEstate(realEstate);
    }
}
