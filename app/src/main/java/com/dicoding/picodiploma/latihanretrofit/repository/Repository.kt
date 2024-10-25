package com.dicoding.picodiploma.latihanretrofit.repository

import com.dicoding.picodiploma.latihanretrofit.api.ApiConfig
import com.dicoding.picodiploma.latihanretrofit.api.ApiService
import com.dicoding.picodiploma.latihanretrofit.ui.detail.DetailUserResponse

class Repository (private val apiService: ApiService){

    suspend fun getUser(username: String): DetailUserResponse {
        return apiService.getDetailUser(username)

    }

    companion object{
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository {
            return instance ?: synchronized(Repository::class.java){
                if (instance == null){
                    instance = Repository(ApiConfig.getApiService())
                }
                return instance as Repository
            }
        }
    }
}