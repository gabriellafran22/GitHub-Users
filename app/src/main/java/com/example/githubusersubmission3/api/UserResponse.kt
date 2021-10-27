package com.example.githubusersubmission3.api

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String

)

data class UserDetailResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("public_repos")
    val publicRepositories: Int
)

data class SearchUserResponse(
    @field:SerializedName("items")
    val items: List<UserResponse>
)
