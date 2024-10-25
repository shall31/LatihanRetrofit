package com.dicoding.picodiploma.latihanretrofit.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.latihanretrofit.R
import com.dicoding.picodiploma.latihanretrofit.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


@Suppress("INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var person: String = ""

    //viewmodel
    private lateinit var detailViewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)


        person = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra("key_users") ?: ""
        }else {
            @Suppress("DEPRECATION")
            intent.getStringExtra("key_users") ?: ""
        }

        //viewmodel
        detailViewModel = ViewModelProvider(this, DetailViewModelFactory.getInstance(application,person ?: "") ).get(
            DetailViewModel::class.java)
        detailViewModel.user.observe(this){
            setUserData(it)
        }


        detailViewModel.isFavorite.observe(this){
            if (it){
                binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
            }else{
                binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
            }
        }


        detailViewModel.isLoading.observe(this) {
            showLoading(isLoading = it)
        }

        setContentView(binding.root)


        //kode untuk menambahkan fragment
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        if (person != null) {
            //viewmodel
            detailViewModel.getUser(person)

            sectionsPagerAdapter.username = person                                                  //menjadikan variabel username yang berada di SectionsPagerAdapter berisikan person
        }

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        //menghubungkan ViewPager dengan TabLayout menggunakan tablayoutmediator
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        //Floating Action Button
        binding.fab.setOnClickListener { view ->

            if (detailViewModel.isFavorite.value == true){
                detailViewModel.deleteFavoriteUser(person, detailViewModel.user.value?.avatarUrl)
                Snackbar.make(view, "User $person dihapus dari favorite !", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }else{
                detailViewModel.insertFavoriteUser(person, detailViewModel.user.value?.avatarUrl)
                Snackbar.make(view, "User $person ditambahkan kedalam favorite !", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setUserData(user: DetailUserResponse) {

        binding.detailName.text = user.name
        Glide.with(binding.detailProfileImage).load(user.avatarUrl).into(binding.detailProfileImage)
        binding.detailFollowers.text = user.followers.toString()
        binding.detailFollowings.text = user.following.toString()
        binding.detailUsername.text = user.login

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }

}


