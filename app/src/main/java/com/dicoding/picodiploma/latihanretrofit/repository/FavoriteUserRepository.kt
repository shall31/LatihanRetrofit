package com.dicoding.picodiploma.latihanretrofit.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.latihanretrofit.database.FavoriteUser
import com.dicoding.picodiploma.latihanretrofit.database.FavoriteUserDao
import com.dicoding.picodiploma.latihanretrofit.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUsersDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUsersDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUsersDao.getALLFavoriteUser()

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute {mFavoriteUsersDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute {mFavoriteUsersDao.delete(favoriteUser)}
    }

    fun getUserFavoriteByUsername(username: String): LiveData<FavoriteUser>{
        return mFavoriteUsersDao.getFavoriteUserByUsername(username)
    }

    companion object{
        @Volatile
        private var INSTANCE: FavoriteUserRepository? = null

        fun getInstance(application: Application): FavoriteUserRepository {
            return INSTANCE ?: synchronized(FavoriteUserRepository::class.java){
                if (INSTANCE == null){
                    INSTANCE = FavoriteUserRepository(application)
                }
                return INSTANCE as FavoriteUserRepository
            }
        }
    }

}