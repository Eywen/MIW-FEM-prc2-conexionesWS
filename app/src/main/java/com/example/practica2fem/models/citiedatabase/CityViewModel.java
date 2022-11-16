package com.example.practica2fem.models.citiedatabase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CityViewModel extends AndroidViewModel {

    private CityRepository cityRepository;
    private LiveData<List<CityEntity>> listCities;

    /**
     * Constructor
     *
     * @param application app
     */
    public CityViewModel (Application application){
        super(application);
        cityRepository = new CityRepository(application);
        listCities = cityRepository.getAll();
    }

    public LiveData<List<CityEntity>> getAll() {
        return listCities;
    }

    public void insert(CityEntity item) {
        cityRepository.insert(item);
    }

    public void deleteAll() {
        cityRepository.deleteAll();
    }

    public void delete(CityEntity item) {
        cityRepository.delete(item);
    }

    public void UpdatePuntuacion(CityEntity puntuacion){cityRepository.updateCity(puntuacion);}

    public CityEntity finById(int idCity) {
        return cityRepository.findById(idCity);
    }
}
