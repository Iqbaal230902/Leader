package com.cpastone.governow.leader.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cpastone.governow.leader.api.ApiService
import com.cpastone.governow.leader.data.model.Post
import java.io.IOException

class StoryPagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, Post>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getAllStories(token, page, params.loadSize).execute()

            if (response.isSuccessful) {
                val responseData = response.body()?.listStory ?: emptyList()

                LoadResult.Page(
                    data = responseData,
                    prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                    nextKey = if (responseData.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(IOException("Failed to load data: ${response.code()} ${response.message()}"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
