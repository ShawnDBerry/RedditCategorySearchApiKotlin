package com.example.android.redditkolinapp.network

import com.example.android.redditkolinapp.model.Reddit
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RedditRetrofitInstance {

    private lateinit var redditService: RedditService

    private var BASE_URL: String = "https://www.reddit.com/"

    init {
        this.redditService = createService(retrofitInstance())
    }

    private fun retrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createService(retrofit: Retrofit): RedditService {
        return retrofit.create(RedditService::class.java)
    }

    fun getCategory(searchCategoryString: String): Observable<Reddit> {
        return redditService.getCategory(searchCategoryString)
    }
}