package com.reihanalavi.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.search.SearchBar
import com.reihanalavi.githubuser.adapter.UserAdapter
import com.reihanalavi.githubuser.data.response.User
import com.reihanalavi.githubuser.data.response.UserResponse
import com.reihanalavi.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user

    private val _searchBar = MutableLiveData<SearchBar>()
    val searchBar: LiveData<SearchBar> = _searchBar

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val DEFAULT_QUERY = "reihan"
    }

    init {
        searchUser(DEFAULT_QUERY)

    }

    private fun searchUser(q: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(q)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _user.value = response.body()?.users

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
}