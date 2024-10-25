package com.dicoding.picodiploma.latihanretrofit.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.latihanretrofit.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<List<ItemsItem>>()                                         //inisialisasi livedata dari class ItemsItem
    val users: LiveData<List<ItemsItem>> = _users                                                   //gapaham. yg jelas menjadikan variabel _users dan dimasukkan kedalam variabel user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    //inisialisasi findUser
    init {
        findUser()
    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(USERS_Q)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {                                                        //jika response success
                    _users.value = response.body()?.items                                           //harusnya disini mengambil data ItemsItem
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    //inisialisasi companion object
    companion object{
        const val TAG = "MainViewModel"
        private const val USERS_Q = "arif"
    }
}