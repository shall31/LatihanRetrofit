package com.dicoding.picodiploma.latihanretrofit.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.latihanretrofit.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserRv()

        favoriteViewModel = ViewModelProvider(this )[FavoriteViewModel::class.java]
        favoriteViewModel.allFavoriteUser.observe(this) {
            adapter.listFavoriteUser = it
        }

    }


    private fun setUserRv(){
        adapter = FavoriteAdapter()
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)

    }

}