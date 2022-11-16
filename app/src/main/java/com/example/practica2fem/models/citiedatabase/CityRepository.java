package com.example.practica2fem.models.citiedatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CityRepository {

    private ICityDAO cityDao;
    private LiveData<List<CityEntity>> listCities;

    public CityRepository (Application application){
        CityRoomDatabase db = CityRoomDatabase.getDatabase(application);
        cityDao = db.grupoDAO();
        listCities = cityDao.getAll();
    }

    public LiveData<List<CityEntity>> getAll() {
        return listCities;
    }

    public long insert(CityEntity cityEntity){ return cityDao.insert(cityEntity);}
    public void updateCity(CityEntity cityEntity){ cityDao.updateCity(cityEntity);}
    public void delete (CityEntity cityEntity){ cityDao.delete(cityEntity);}
    public void deleteAll(){ cityDao.deleteAll();}

    public CityEntity findById(int idCity) { return cityDao.finById(idCity);}
}
