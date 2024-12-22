package com.sample.itunes.remote

import com.sample.itunes.model.AllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiConstants.API_SEARCH)
    suspend fun getSearchList(
        @Query(ApiConstants.TERM) term: String): AllResponse

    @GET(ApiConstants.API_SEARCH)
    suspend fun getListDeatils(
        @Query(ApiConstants.TERM) term: String,
        @Query(ApiConstants.MEDIA) media: String,
        @Query(ApiConstants.LIMIT) limit: String
    ): AllResponse

}
