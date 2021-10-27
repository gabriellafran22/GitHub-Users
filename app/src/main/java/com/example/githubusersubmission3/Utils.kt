package com.example.githubusersubmission3

import com.example.githubusersubmission3.api.UserResponse
import com.example.githubusersubmission3.database.Favorites

object Utils {

    fun DatabaseToAPI(favUsers: List<Favorites>): List<UserResponse> {
        val listUserResponse = ArrayList<UserResponse>()
        favUsers.forEach() {
            var temp = UserResponse(
                it.username!!,
                it.avatar_url!!
            )
            listUserResponse.add(temp)
        }
        return listUserResponse
    }

}