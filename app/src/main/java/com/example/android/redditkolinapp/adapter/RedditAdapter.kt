package com.example.android.redditkolinapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.redditkolinapp.R
import com.example.android.redditkolinapp.model.Child
import com.example.android.redditkolinapp.model.Reddit
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.category_result_itemview.view.*

class RedditAdapter(
    private var childList: List<Child>, private var redditItemDelegate: RedditItemDelegate,
    private var applicationContext: Context
) : RecyclerView.Adapter<RedditAdapter.RedditAdapterViewHolder>() {

    interface RedditItemDelegate {
        fun viewRedditItem(child: Child)
    }

    class RedditAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userName: TextView = itemView.user_name
        var body: TextView = itemView.body
        var comments: TextView = itemView.comments
        var ups: TextView = itemView.ups
        var downs: TextView = itemView.downs
        var avatar: ImageView = itemView.avatar_pic_image_view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_result_itemview, parent, false)
        return RedditAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: RedditAdapterViewHolder, position: Int) {
        holder.userName.text = childList[position].data.author
        holder.body.text = childList[position].data.title
        holder.comments.text = applicationContext.getString(R.string.comment_num_text, childList[position].data.numComments)
        holder.ups.text = applicationContext.getString(R.string.likes_text, childList[position].data.ups)
       /* holder.downs.text = String.format("dislike %d",childList[position].data.downs)*/
        holder.downs.text = applicationContext.getString(R.string.dislikes_text, childList[position].data.downs)

        Glide.with(applicationContext).load(childList.get(position).getData().getThumbnail())
            .apply(RequestOptions.circleCropTransform()).into(holder.avatar)

        holder.itemView.setOnClickListener {
            redditItemDelegate.viewRedditItem(childList[position])
        }


    }
}