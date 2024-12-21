package com.sample.itunes.paging

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.itunes.model.ITunesResponse
import com.sample.itunes.model.ResultsItem
import com.sample.itunes.remote.ApiConstants
import com.sample.itunes.remote.ApiInterface
import retrofit2.Response

class ITunesPagingSource(
    private val apiInterface: ApiInterface,
    private val term: String,
    private val mediaTypes: List<String>
) :
    PagingSource<Int, ResultsItem>() {


    @SuppressLint("SuspiciousIndentation")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
       var response: Response<ITunesResponse>?=null
            return try {
                val page = params.key ?: 0
                val limit = ApiConstants.NETWORK_PAGE_LIMIT

                for (media in mediaTypes) {
                    response = apiInterface.getSearchDeatils(term, media, limit.toString())
                }
                LoadResult.Page(
                    data = response!!.body()!!.results, prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.body()!!.results.isEmpty()) null else page + 1
                )

            } catch (e: Exception) {
                LoadResult.Error(e)
            }

    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
