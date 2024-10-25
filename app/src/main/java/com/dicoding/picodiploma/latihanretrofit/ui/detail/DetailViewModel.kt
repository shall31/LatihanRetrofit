package com.dicoding.picodiploma.latihanretrofit.ui.detail

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.dicoding.picodiploma.latihanretrofit.database.FavoriteUser
import com.dicoding.picodiploma.latihanretrofit.repository.FavoriteUserRepository
import com.dicoding.picodiploma.latihanretrofit.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel(val repository: Repository, val favoriteUserRepository: FavoriteUserRepository, username: String): ViewModel() {
    val user = MutableLiveData<DetailUserResponse>()
    val userFavorite = favoriteUserRepository.getUserFavoriteByUsername(username)
    val isLoading = MutableLiveData<Boolean>()
    private val _isFavorite = MediatorLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        _isFavorite.addSource(userFavorite) {
            _isFavorite.value = it != null
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            isLoading.value = true
            user.value = repository.getUser(username)                                               //dapetin user data
            isLoading.value = false
        }
    }


    fun insertFavoriteUser(username: String, avatarUrl: String?){
        val favoriteUser: FavoriteUser = FavoriteUser(username, avatarUrl)
        favoriteUserRepository.insert(favoriteUser)
    }

    fun deleteFavoriteUser(username: String, avatarUrl: String?){
        val favoriteUser: FavoriteUser = FavoriteUser(username, avatarUrl)
        favoriteUserRepository.delete(favoriteUser)
    }

}



//factory
class DetailViewModelFactory private constructor(
        private val repository: Repository, private val favoriteUserRepository: FavoriteUserRepository, private val username: String
    ): ViewModelProvider.Factory{

        companion object{
            @Volatile
            private var instance: DetailViewModelFactory? = null

            fun getInstance(application: Application, username: String): DetailViewModelFactory = instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(Repository.getInstance(), FavoriteUserRepository.getInstance(application), username)

            }
        }

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(DetailViewModel::class.java)->{
                DetailViewModel(repository, favoriteUserRepository, username) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }


}