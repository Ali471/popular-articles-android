package com.example.newsapp.view

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.NewsMediaModel
import kotlinx.android.synthetic.main.activity_news_detail.*


class NewsDetailActivity : AppCompatActivity() {

    lateinit var newsItem:NewsMediaModel.Root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        newsItem        =   intent.getSerializableExtra("newsItem") as NewsMediaModel.Root
        setUpToolBar()
        initView()
        clickListeners()
    }

    private fun initView() {
        post_title.text     =   newsItem.postTitle
        post_date.text      =   newsItem.postDate
        post_author.text    =   newsItem.postAuthor
        post_desc.text      =   newsItem.postDetail
        post_title.text     =   newsItem.postTitle
        post_title.text     =   newsItem.postTitle
        Glide.with(this)
            .load(newsItem.postMedia?.get(0)?.postImages?.get(0)?.imageUrl)
            .into(post_Image)
    }

    private fun clickListeners(){
        btn_see_more.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.postUrl))
            startActivity(browserIntent)
        }
    }


    private fun setUpToolBar(){
        val ab = supportActionBar
        ab?.title = "News Detail"
        ab?.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material)
        ab?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,R.color.colorPrimary)))
        ab?.setDisplayShowCustomEnabled(true)
        ab?.setDisplayShowTitleEnabled(true)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

}
