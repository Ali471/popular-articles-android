package com.example.newsapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.BuildConfig
import com.example.newsapp.managers.ApiInterface
import com.example.newsapp.managers.ApiManager
import com.example.newsapp.model.NewsMediaModel
import com.example.newsapp.model.response_model.ResponseModel
import com.example.newsapp.util.ConnectivityUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object NewsRepository {

    /**
     * Mutable Live data Objects for response and error handling
     */
    var mResponse: MutableLiveData<ArrayList<NewsMediaModel.Root>> = MutableLiveData()
    var mError: MutableLiveData<String> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * This method gets popular articles from NY times api using retrofit
     * @param days
     */
    fun getPopularNews(days:Int){
        if (ConnectivityUtil.isNetworkAvailable()){
            isLoading.value = true
            val apiInterface    =   ApiManager.getClient().create(ApiInterface::class.java)
            val newsResponse    =   apiInterface.getPopularNews(days,BuildConfig.Api_Key)
            newsResponse.enqueue(object : Callback<ResponseModel<ArrayList<NewsMediaModel.Root>>> {
                override fun onFailure(call: Call<ResponseModel<ArrayList<NewsMediaModel.Root>>>, t: Throwable) {
                    isLoading.value = false
                    mError.value =   t.message
                }
                override fun onResponse(
                    call: Call<ResponseModel<ArrayList<NewsMediaModel.Root>>>,
                    response: Response<ResponseModel<ArrayList<NewsMediaModel.Root>>>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful && response.body() != null){
                        if (response.body()?.status=="OK")
                            mResponse.value =   response.body()?.results
                        else
                            mError.value    =   response.body()?.error?.get(0) ?:"Error Try Again"
                    } else {
                        mError.value    =   response.message()
                    }

                }

            })
        } else {
            isLoading.value = false
            mError.value    =   "No Internet Connection"
        }
    }
}