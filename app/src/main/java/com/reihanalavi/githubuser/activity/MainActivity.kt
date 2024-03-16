package com.reihanalavi.githubuser.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.reihanalavi.githubuser.adapter.UserAdapter
import com.reihanalavi.githubuser.data.response.User
import com.reihanalavi.githubuser.data.response.UserResponse
import com.reihanalavi.githubuser.data.retrofit.ApiConfig
import com.reihanalavi.githubuser.databinding.ActivityMainBinding
import com.reihanalavi.githubuser.ui.theme.GithubUserTheme
import com.reihanalavi.githubuser.ui.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val DEFAULT_QUERY = "reihan"
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.hide()

        mainViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory())
                .get(MainViewModel::class.java)

        mainViewModel.user.observe(this) { user ->
            setUserData(user)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchUser(searchView.text.toString())
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    false
                }
        }
    }

    private fun searchUser(q: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(q)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        setUserData(responseBody.users)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun setUserData(user: List<User>) {
        val adapter = UserAdapter()
        adapter.submitList(user)

        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.pbMain.visibility = View.VISIBLE
        } else {
            binding.pbMain.visibility = View.GONE
        }
    }
}