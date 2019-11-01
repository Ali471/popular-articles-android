package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.NewsMediaModel
import com.example.newsapp.repositories.NewsRepository

class NewsViewModel: ViewModel() {

    private var response: MutableLiveData<ArrayList<NewsMediaModel.Root>>? = null
     private var mError: MutableLiveData<String>? = null
     private var isLoading: MutableLiveData<Boolean>? = null

    /**
     * This method fetch data from repository and assign to LiveData objects
     * @param days
     */
    fun init(days:Int){
        NewsRepository.getPopularNews(days)
        response    = NewsRepository.mResponse
        mError      = NewsRepository.mError
        isLoading   = NewsRepository.isLoading
    }

    /**
     * Returns News List response to observer
     */
    fun getNews(): LiveData<ArrayList<NewsMediaModel.Root>>? {
        return response
    }

    /**
     * Returns String Error response to observer
     */
    fun getError(): LiveData<String>? {
        return mError
    }

    /**
     * Returns Boolean response to observer
     */
    fun isLoading(): LiveData<Boolean>? {
        return isLoading
    }

}