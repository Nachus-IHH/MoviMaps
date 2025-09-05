package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.entity.User;

@Dao
public interface UserDao {
    @Insert
    long insertar(User user);

    @Update
    int actualizar(User user);

    @Delete
    int delete(User user);

    @Query("SELECT * FROM User WHERE id_user = :id")
    User getPorId(int id);
}
