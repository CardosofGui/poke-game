package com.example.pokegame.data

import com.example.pokegame.domain.Result
import com.example.pokegame.domain.UserPoints
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface RecordInterface {

    @FormUrlEncoded
    @POST("insert")
    fun insertRecord(
        @Field("pokegame_username") username : String,
        @Field("pokegame_points") points : String,
        @Field("pokegame_team") team : String,
        @Field("pokegame_person") person : String
    ) : Call<Result>

    @GET("all")
    fun getAllRecords() : Call<List<UserPoints>>

}