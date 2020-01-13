package com.example.android.redditkolinapp.network

import com.example.android.redditkolinapp.model.Reddit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {

    @GET("/r/{category}/.json")
    fun getCategory(
        @Path("category") category: String): Observable<Reddit>
}