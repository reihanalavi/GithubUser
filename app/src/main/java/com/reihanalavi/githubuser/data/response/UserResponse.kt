package com.reihanalavi.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class User (
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("gravatar_url")
    val gravatarUrl: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,
)

data class UserResponse(
    @field:SerializedName("items")
    val users: List<User>,

    @field:SerializedName("incomplete_results")
    val incompleteResult: Boolean,
)