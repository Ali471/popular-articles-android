package com.example.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.NewsMediaModel
import kotlinx.android.synthetic.main.layout_news_list.view.*

class NewsAdapter(private val context: Context,
                  private val newsList:ArrayList<NewsMediaModel.Root>,
                  private val clickListener:(NewsMediaModel.Root)-> Unit): RecyclerView.Adapter<NewsAdapter.CustomViewHolder>() {

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun getItemId(position: Int): Long {
        return newsList[position].id!!.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val messageView = LayoutInflater.from(parent.context).inflate(R.layout.layout_news_list, parent, false)
        return CustomViewHolder(messageView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(newsList[position], clickListener)
    }

    inner class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(newsItem: NewsMediaModel.Root, clickListener: (NewsMediaModel.Root) -> Unit) {
            view.post_title.text    =   newsItem.postTitle
            view.post_author.text   =   newsItem.postAuthor
            view.post_type.text     =   newsItem.postType
            view.post_date.text     =   newsItem.postDate

            //there are multiple images so getting first image only
            Glide.with(context).load(newsItem.postMedia?.get(0)?.postImages?.get(0)?.imageUrl).into(view.post_image)

            view.setOnClickListener { clickListener(newsItem) }
        }
    }

}