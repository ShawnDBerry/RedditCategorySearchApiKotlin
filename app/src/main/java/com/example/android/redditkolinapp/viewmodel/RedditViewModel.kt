package com.example.android.redditkolinapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.redditkolinapp.model.Reddit
import com.example.android.redditkolinapp.network.RedditRetrofitInstance
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RedditViewModel(application: Application) : AndroidViewModel(application) {

    val redditRetrofit = RedditRetrofitInstance()

    fun getCategory(searchCategoryString: String): Observable<Reddit> {
        return redditRetrofit.getCategory(searchCategoryString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}