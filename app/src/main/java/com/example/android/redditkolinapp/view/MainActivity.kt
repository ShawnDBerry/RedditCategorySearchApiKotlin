package com.example.android.redditkolinapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.redditkolinapp.R
import com.example.android.redditkolinapp.adapter.RedditAdapter
import com.example.android.redditkolinapp.model.Child
import com.example.android.redditkolinapp.model.Reddit
import com.example.android.redditkolinapp.viewmodel.RedditViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RedditAdapter.RedditItemDelegate {

    private var compositeDisposable = CompositeDisposable()

    lateinit var viewModel: RedditViewModel

    private var redditAdapter: RedditAdapter? = null

    private var layoutManager: RecyclerView.LayoutManager? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(RedditViewModel::class.java)

        compositeDisposable.add(
            viewModel.getCategory("funny")
                .subscribe({ categoryList ->
                    printResults(categoryList.data.children)
                    setUpRV(categoryList.data.children)
                }, { throwable ->
                    Log.d("TAG_Q", "error " + throwable.message)
                })
        )

        category_search_button.setOnClickListener {

            compositeDisposable.add(
                viewModel.getCategory(category_search_view.query.toString())
                    .subscribe({ categoryList ->
                        printResults(categoryList.data.children)
                        setUpRV(categoryList.data.children)
                    }, { throwable ->
                        Log.d("TAG_Q", "error " + throwable.message)
                    })
            )
        }

    }
    private fun setUpRV(result: List<Child>){
        redditAdapter = RedditAdapter(result, this,this)
        layoutManager = LinearLayoutManager(this)
        category_search_recyclerview.adapter = redditAdapter
        category_search_recyclerview.layoutManager = layoutManager
        val decorater: RecyclerView.ItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        category_search_recyclerview.addItemDecoration(decorater)

    }
    private fun printResults(categoryList: List<Child>) {
        categoryList.forEach { listItem ->
            Log.d("TAG_Q", " " + listItem.data.author)
        }
    }

    override fun viewRedditItem(child: Child) {
        var toSend = "Check out what %s said on Reddit: \"%s\""
        toSend = String.format(toSend,child.data.author, child.data.title)
        Log.d("TAG_Q", "onClick: " + toSend)
        var intent = Intent(Intent.ACTION_SEND);
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, toSend)

        startActivity(Intent.createChooser(intent, "Send mail..."))

    }
}
