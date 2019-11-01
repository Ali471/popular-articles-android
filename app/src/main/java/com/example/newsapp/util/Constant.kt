package com.example.newsapp.util

object Constant {
    const val CONNECTION_TIMEOUT: Long = 30
    const val READ_TIMEOUT: Long = 30

    public enum class NewFilter(val value:Int){
        DAY(1),
        WEEK(7),
        MONTH(30)
    }
}