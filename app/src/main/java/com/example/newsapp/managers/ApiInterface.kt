package com.example.newsapp.managers


import com.example.newsapp.model.NewsMediaModel
import com.example.newsapp.model.response_model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    /**
     *
     *@param days
     * @param apiKey
     */
    @GET("all-sections/{days}")
    fun getPopularNews(@Path("days") days:Int,@Query("api-key") apiKey:String): Call<ResponseModel<ArrayList<NewsMediaModel.Root>>>
}