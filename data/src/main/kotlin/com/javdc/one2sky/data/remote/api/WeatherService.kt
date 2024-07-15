package com.javdc.one2sky.data.remote.api

import com.javdc.one2sky.data.remote.constant.WTTR_API_FORMAT
import com.javdc.one2sky.data.remote.constant.WTTR_API_LANGUAGE
import com.javdc.one2sky.data.remote.model.WttrResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("/")
    suspend fun getWeather(
        @Query("format") format: String = WTTR_API_FORMAT,
        @Query("lang") lang: String = WTTR_API_LANGUAGE
    ): WttrResponseDto

    @GET("/{query}")
    suspend fun getWeatherForQuery(
        @Path("query") query: String,
        @Query("format") format: String = WTTR_API_FORMAT,
        @Query("lang") lang: String = WTTR_API_LANGUAGE
    ): WttrResponseDto

}
