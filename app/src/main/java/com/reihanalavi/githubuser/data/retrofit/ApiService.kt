package com.reihanalavi.githubuser.data.retrofit

import com.reihanalavi.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q")
        q: String
    ): Call<UserResponse>
}