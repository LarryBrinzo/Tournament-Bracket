package com.bracket.Network

import com.bracket.Models.GridDataClass
import retrofit2.Call
import retrofit2.http.GET

interface FinalsGridEndpoint{

    @GET("")
    fun getFinalGrid(): Call<GridDataClass>

}