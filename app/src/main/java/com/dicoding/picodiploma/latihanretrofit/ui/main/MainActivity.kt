package com.dicoding.picodiploma.latihanretrofit.ui.main


import android.app.SearchManager
import androidx.appcompat.widget.SearchView
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.latihanretrofit.R
import com.dicoding.picodiploma.latihanretrofit.api.ApiConfig
import com.dicoding.picodiploma.latihanretrofit.databinding.ActivityMainBinding
import com.dicoding.picodiploma.latihanretrofit.ui.favorite.FavoriteActivity
import com.dicoding.picodiploma.latihanretrofit.ui.settings.SettingPreferences
import com.dicoding.picodiploma.latihanretrofit.ui.settings.SettingsActivity
import com.dicoding.picodiploma.latihanretrofit.ui.settings.SettingsViewModel
import com.dicoding.picodiploma.latihanretrofit.ui.settings.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    //livedata
    private lateinit var binding: ActivityMainBinding
    private val _users = MutableLiveData<List<ItemsItem>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)



        //fungsi untuk memanggil findUser, findUser akan diubah agar dapat dipanggil dari mainviewmodel
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        mainViewModel.users.observe(this) { _users ->
            setUserData(_users)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }


    }

    private fun setUserData(user: List<ItemsItem>) {
        _users.value = user

        //mem-binding data dan dimasukkan ke dalam recyclerview rvReview melalui adapter
        val adapter = UsersAdapter(user)
        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    //SearchView
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)                                                  //inflate option menu agar menjadi toolbar main activity

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView


        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)                                                      //Masukkan kata
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            //fungsi yang digunakan setelah user memencet ok
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(isLoading = true)

                //fungsi untuk menampilkan data yang telah disubmit dengan "query"
                val client = ApiConfig.getApiService().getUser(query)
                client.enqueue(object : Callback<UserResponse>{
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {                                                //jika response success
                            showLoading(isLoading = false)
                            _users.value = response.body()?.items                                   //memasukkan response body ke dalam _users.value

                            var user: List<ItemsItem>? = _users.value                               //menjadikan variabel user (yg berisi List<ItemsItem>? berisikan data dari _users
                            val adapter = user?.let { UsersAdapter(it) }                            //variabel adapter menampung data user
                            binding.rvReview.adapter = adapter                                      //binding ke dalam recylerview

                        }else{
                            Log.e(MainViewModel.TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.e(MainViewModel.TAG, "onFailure: ${t.message}")
                    }
                })
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.favorite -> {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
            }
            R.id.settings -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
        }
        return true
    }

}