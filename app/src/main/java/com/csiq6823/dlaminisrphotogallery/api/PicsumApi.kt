package com.csiq6823.dlaminisrphotogallery.api

import com.csiq6823.dlaminisrphotogallery.GalleryItem
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {
    @GET("v2/list")
    suspend fun fetchPhotos(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100
    ): List<GalleryItem>
}
