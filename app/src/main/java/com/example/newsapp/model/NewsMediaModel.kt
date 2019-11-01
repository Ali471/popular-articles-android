package com.example.newsapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.ArrayList

class NewsMediaModel{
    inner class Root : Serializable {
        @SerializedName("id")
        var id: Long? =   null
        @SerializedName("media")
        var postMedia: ArrayList<PostMedia>?   =   null
        @SerializedName("title")
        var postTitle: String? = null
        @SerializedName("abstract")
        var postDetail: String? =   null
        @SerializedName("byline")
        var postAuthor: String? = null
        @SerializedName("url")
        var postUrl: String? = null
        @SerializedName("type")
        var postType:String?    =   null
        @SerializedName("published_date")
        var postDate: String? = null
    }

    inner class PostMedia : Serializable {
        @SerializedName("type")
        var type: String? = null
        @SerializedName("media-metadata")
        var postImages: ArrayList<PostImages> = ArrayList()
    }

    inner class PostImages: Serializable {
        @SerializedName("url")
        var imageUrl: String? = null
        @SerializedName("height")
        var imageHeight: String? = null
        @SerializedName("width")
        var imageWidth: String? = null

    }

}

