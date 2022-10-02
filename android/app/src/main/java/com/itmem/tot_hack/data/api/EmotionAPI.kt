package com.itmem.tot_hack.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface EmotionAPI {
    @GET("/get_emotion/")
    suspend fun recognize(
        @Query("messages") messages: String
    ): String
}