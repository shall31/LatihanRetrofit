package com.dicoding.picodiploma.latihanretrofit.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.latihanretrofit.database.FavoriteUser
import com.dicoding.picodiploma.latihanretrofit.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository.getInstance(application)

    val allFavoriteUser: LiveData<List<FavoriteUser>> = favoriteUserRepository.getAllFavoriteUsers()
}