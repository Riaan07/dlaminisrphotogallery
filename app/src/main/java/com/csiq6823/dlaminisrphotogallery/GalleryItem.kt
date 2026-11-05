package com.csiq6823.dlaminisrphotogallery

import com.squareup.moshi.Json


data class GalleryItem(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @Json(name = "download_url") val downloadUrl: String
)
