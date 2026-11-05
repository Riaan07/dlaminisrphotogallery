package com.csiq6823.dlaminisrphotogallery

import com.csiq6823.dlaminisrphotogallery.api.PicsumApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PhotoRepository {
    private val api: PicsumApi = Retrofit.Builder()
        .baseUrl("https://picsum.photos/")
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(PicsumApi::class.java)

    suspend fun fetchPhotos(page: Int = 1, limit: Int = 100): List<GalleryItem> =
        api.fetchPhotos(page, limit)
}
