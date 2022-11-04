package com.lssoft2022.letsapp

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("51544c6447746b6436397461546c75/json/ListPublicReservationSport/1/50/")
    fun searchData(@Query("MINCLASSNM") MINCLASSNM: String)

}