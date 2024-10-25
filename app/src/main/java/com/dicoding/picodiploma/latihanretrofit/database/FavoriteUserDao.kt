package com.dicoding.picodiploma.latihanretrofit.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = FavoriteUser::class)
    fun insert(favoriteUser: FavoriteUser)

    @Delete(entity = FavoriteUser::class)
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from favorite_users ORDER BY username ASC")
    fun getALLFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite_users WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>


}