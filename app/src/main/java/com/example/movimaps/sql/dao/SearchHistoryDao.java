package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.database.SearchHistory;
import com.example.movimaps.sql.entity.Parada;
@Dao
public interface SearchHistoryDao {


    @Insert
    long insertar(SearchHistory searchHistory);

    @Update
    int actualizar(SearchHistory searchHistory);

    @Delete
    int delete(SearchHistory searchHistory);

    @Query("SELECT * FROM search_history WHERE id = :id")
    SearchHistory getPorId(int id);
}
