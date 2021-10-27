package com.example.githubusersubmission3.api

import retrofit2.Call
import retrofit2.http.*
import com.example.githubusersubmission3.BuildConfig.GITHUB_TOKEN

interface ApiService {

    @GET("users")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUsers(): Call<List<UserResponse>>

    @GET("search/users")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getSearchUsers(
        @Query("q") username: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getUserDetailByUsername(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>
}