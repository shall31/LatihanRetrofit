package com.dicoding.picodiploma.latihanretrofit.api

import com.dicoding.picodiploma.latihanretrofit.ui.detail.DetailUserResponse
import com.dicoding.picodiploma.latihanretrofit.ui.main.ItemsItem
import com.dicoding.picodiploma.latihanretrofit.ui.main.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //fungsi ApiService.kt untuk mengambil data yang berasal dari github
    @GET("search/users")                                                                            //mengambil data dari endpoint search/users

    fun getUser(                                                                                    //fungsi yang dipanggil di MainActivity
        //query untuk search
        @Query("q")q: String
    ): Call<UserResponse>

    //fungsi yang dipanggil di ketika masuk ke DetailActivity
    @GET("users/{username}")

    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

    //fungsi untuk get followers
    @GET("users/{username}/followers")

    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")

    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>



}