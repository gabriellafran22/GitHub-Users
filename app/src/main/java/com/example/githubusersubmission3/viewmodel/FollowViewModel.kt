package com.example.githubusersubmission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission3.api.ApiConfig
import com.example.githubusersubmission3.api.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _following = MutableLiveData<List<UserResponse>>()
    val following: LiveData<List<UserResponse>> = _following

    private val _followers = MutableLiveData<List<UserResponse>>()
    val followers: LiveData<List<UserResponse>> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetailFollowFromAPI(username: String, position: Int) {
        _isLoading.value = true
        val client = if (position == 0) {
            ApiConfig.getApiService().getUserFollowers(username)
        } else {
            ApiConfig.getApiService().getUserFollowing(username)
        }
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        when (position) {
                            0 -> _followers.value = response.body()
                            1 -> _following.value = response.body()
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }
}