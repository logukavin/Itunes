package com.sample.itunes.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.itunes.model.AllResponse
import com.sample.itunes.model.ResultsItem
import com.sample.itunes.paging.ITunesPagingSource
import com.sample.itunes.remote.ApiConstants
import com.sample.itunes.remote.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiInterface: ApiInterface) {
    fun getSearchDeatils(term: String, mediaTypes: List<String>): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.NETWORK_PAGE_LIMIT,
                enablePlaceholders = true,
                initialLoadSize = 2
            ), pagingSourceFactory = { ITunesPagingSource(apiInterface,term,mediaTypes) }, initialKey = 1
        ).flow
    }



    suspend fun getSearchListDeatils(term: String, mediaTypes: String): Flow<AllResponse> = flow {
            emit(apiInterface.getSearchListDeatils(term,mediaTypes,ApiConstants.NETWORK_PAGE_LIMIT.toString()))
    }


}
