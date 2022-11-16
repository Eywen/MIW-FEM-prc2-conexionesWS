package com.example.practica2fem.models.citiedatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ICityDAO {
    @Query("SELECT * FROM " + CityEntity.TABLA)
    LiveData<List<CityEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(CityEntity cityEntity);

    @Update
    void updateCity(CityEntity cityEntity);

    @Delete
    void delete(CityEntity cityEntity);

    @Query("DELETE FROM " + CityEntity.TABLA)
    void deleteAll();

    @Query("SELECT * FROM " + CityEntity.TABLA + " WHERE id = :idCity" )
    CityEntity finById(int idCity);

    @Query("SELECT * FROM " + CityEntity.TABLA + " WHERE name = :cityName")
    List<CityEntity> findByName(String cityName);
}
