package com.sample.itunes.remote

import com.sample.itunes.model.AllResponse
import com.sample.itunes.model.ITunesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET(ApiConstants.API_SEARCH)
    suspend fun getSearchDeatils(
        @Query(ApiConstants.TERM) term: String,
        @Query(ApiConstants.MEDIA) media: String,
        @Query(ApiConstants.LIMIT) limit: String
    ): Response<ITunesResponse>

    @GET(ApiConstants.API_SEARCH)
    suspend fun getSearchListDeatils(
        @Query(ApiConstants.TERM) term: String,
        @Query(ApiConstants.MEDIA) media: String,
        @Query(ApiConstants.LIMIT) limit: String
    ): AllResponse

}
