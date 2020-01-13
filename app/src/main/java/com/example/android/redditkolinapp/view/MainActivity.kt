package com.example.android.redditkolinapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.android.redditkolinapp.R
import com.example.android.redditkolinapp.model.Child
import com.example.android.redditkolinapp.model.Reddit
import com.example.android.redditkolinapp.viewmodel.RedditViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    lateinit var viewModel: RedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(RedditViewModel::class.java)

        compositeDisposable.add(
            viewModel.getCategory(/*category_search_view.query.toString()*/"funny")
                .subscribe({ categoryList ->
                    printResults(categoryList.data.children)
                }, { throwable ->
                    Log.d("TAG_Q", "error " + throwable.message)
                })
        )
    }

    private fun printResults(categoryList: List<Child>) {
        categoryList.forEach { listItem ->
            Log.d("TAG_Q", " " + listItem.data.author)
        }
    }
}
