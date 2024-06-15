package com.cpastone.governow.leader.database.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cpastone.governow.leader.data.model.Post

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(story: Post)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, Post>

    @Query("SELECT * FROM story")
    fun getAllStoryTest(): List<Post>

    @Query("DELETE FROM story")
    fun deleteAll()
}
