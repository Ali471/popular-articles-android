package com.example.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.model.NewsMediaModel
import com.example.newsapp.util.Constant
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.toolbar_main.*


class NewsActivity : AppCompatActivity() {

    private var newsViewModel:NewsViewModel?    =   null
    private var newsAdapter:NewsAdapter?        =   null
    private var selectedTime    =   Constant.NewFilter.DAY.value
    private var newsList: ArrayList<NewsMediaModel.Root> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setUpToolBar()
        initView()
        clickListeners()
    }

    //Initialization of view model and adding observer on activity start
    private fun initView(){
        newsViewModel   =   ViewModelProviders.of(this).get(NewsViewModel::class.java)
        showProgressBar()
        newsViewModel?.init(Constant.NewFilter.DAY.value)

        //  Observer to get latest news based on filter
        newsViewModel?.getNews()?.observe(this, Observer {
            if (newsAdapter == null) {
                newsList.addAll(it)
                initRecycler(newsList)
            } else {
                newsList.clear()
                newsList.addAll(it)
                newsAdapter?.notifyDataSetChanged()
            }
        })

        //  Observer for getting errors while loading News
        newsViewModel?.getError()?.observe(this, Observer {
            Snackbar.make(root,it.toString(),Snackbar.LENGTH_LONG).show()
        })

        //  Observer to check if record is updating
        newsViewModel?.isLoading()?.observe(this, Observer {
            if (it){
                showProgressBar()
            } else{
                hideProgressBar()
            }
        })

    }

    /**
     * @param newsList
     * Recycler initialization and setting adapter
     */
    private fun initRecycler(newsList: ArrayList<NewsMediaModel.Root>) {
        val layoutManager   =   LinearLayoutManager(this)
        val mDividerItemDecoration = DividerItemDecoration(news_recycler.context, layoutManager.orientation)
        news_recycler.layoutManager =   layoutManager
        news_recycler.addItemDecoration(mDividerItemDecoration)
        newsAdapter = NewsAdapter(this, newsList) { singleItem: NewsMediaModel.Root -> itemClicked(singleItem) }
        news_recycler.adapter = newsAdapter
    }


    /**
     * Recycler Item click Listener
     * @param singleNewsItem
     */
    private fun itemClicked(singleNewsItem: NewsMediaModel.Root){
        val intent  =   Intent(this,NewsDetailActivity::class.java)
        intent.putExtra("newsItem",singleNewsItem)
        startActivity(intent)
    }


    /**
     * all click events in current activity
     */
    private fun clickListeners(){
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_filter->showFilterDialog()
                R.id.menu_refresh->updateNews()
            }
            return@setOnMenuItemClickListener  true
        }

        swiperefresh.setOnRefreshListener {
            updateNews()
        }

    }

    /**
     * Show Filter dialog
     */
    private fun showFilterDialog(){
        val stringArray =   resources.getStringArray(R.array.news_filter)
        val itemSelected = when(selectedTime){
            Constant.NewFilter.DAY.value-> 0
            Constant.NewFilter.WEEK.value-> 1
            Constant.NewFilter.MONTH.value-> 2
            else -> 0
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_filter))
            .setSingleChoiceItems(stringArray, itemSelected) { _, selectedIndex ->
                selectedTime = when(selectedIndex){
                    0-> Constant.NewFilter.DAY.value
                    1-> Constant.NewFilter.WEEK.value
                    2-> Constant.NewFilter.MONTH.value
                    else -> Constant.NewFilter.DAY.value
                }
            }
            .setPositiveButton(getString(R.string.ok_text)) { dialog, _ ->
                updateNews()
                dialog.dismiss()
            }
            .show()
    }

    //Update news record based on user event e.g filtering, swipe to refresh ect
    private fun updateNews(){
        newsViewModel?.init(selectedTime)
    }

    private fun showProgressBar() {
        progress_circular.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        swiperefresh.isRefreshing   =   false
        progress_circular.visibility = View.GONE
    }

    //set custom toolbar
    private fun setUpToolBar(){
        setSupportActionBar(toolbar)
    }


    // Menu icons are inflated just as they were with actionbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

}
