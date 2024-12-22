package com.sample.itunes.repository

import com.sample.itunes.model.AllResponse
import com.sample.itunes.remote.ApiConstants
import com.sample.itunes.remote.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getSearchListDeatils(term: String, mediaTypes: String): Flow<AllResponse> = flow {
        emit(
            apiInterface.getSearchListDeatils(
                term,
                mediaTypes,
                ApiConstants.NETWORK_PAGE_LIMIT.toString()
            )
        )
    }

}
