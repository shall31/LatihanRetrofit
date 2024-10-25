package com.dicoding.picodiploma.latihanretrofit.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.latihanretrofit.ui.main.ItemsItem
import com.dicoding.picodiploma.latihanretrofit.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    private val _listFollowings = MutableLiveData<List<ItemsItem>>()

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    _listFollowers.value = response.body()

                }else{
                    Log.d("Failure", response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getListFollowers() : LiveData<List<ItemsItem>>{
        return _listFollowers
    }

    fun setListFollowings(username: String){
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    _listFollowings.value = response.body()

                }else{
                    Log.d("Failure", response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getListFollowings() : LiveData<List<ItemsItem>>{
        return _listFollowings
    }

}