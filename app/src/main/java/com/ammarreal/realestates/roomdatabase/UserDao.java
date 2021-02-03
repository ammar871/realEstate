package com.ammarreal.realestates.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ammarreal.realestates.pojo.HomeModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<HomeModel> getAll();

    @Insert(onConflict = REPLACE)
    void insertUser(HomeModel mUser);

    @Insert
    void insertAllUser(HomeModel... mUsersList);

    @Delete
    void delete(HomeModel mUser);

    @Update
    void updateUser(HomeModel mUser);

    @Query("SELECT * FROM user WHERE uid = :uId")
    HomeModel getUserById(int uId);

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<HomeModel> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    boolean findByName(String name);
}
