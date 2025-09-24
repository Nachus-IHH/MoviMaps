package com.example.movimaps.sql.database;  // Usa solo este paquete

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM User WHERE id_user = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM User WHERE usuario = :usuario")
    User getUserByUsername(String usuario);

    @Query("SELECT * FROM User WHERE correo = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM User WHERE usuario = :usuario AND password = :password")
    User loginUser(String usuario, String password);

    @Query("SELECT * FROM User")
    List<User> getAllActiveUsers();

    @Query("SELECT COUNT(*) FROM User WHERE usuario = :usuario")
    int checkUsernameExists(String usuario);

    @Query("SELECT COUNT(*) FROM User WHERE correo = :email")
    int checkEmailExists(String email);
}
