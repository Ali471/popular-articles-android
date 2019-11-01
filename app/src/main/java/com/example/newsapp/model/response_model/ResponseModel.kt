package com.example.newsapp.model.response_model

data class ResponseModel<T>(
    val status: String? = null,
    val num_results: Int? = null,
    val copyright: String? = null,
    val error: ArrayList<String>?   =   null,
    val results: T? = null
)
