package com.riridev.ririapp.data.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.riridev.ririapp.data.local.room.entity.DiscussionEntity

@Dao
interface DiscussionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscussion(discussion: List<DiscussionEntity>)

    @Query("select * from discussion")
    fun getAllDiscussion(): PagingSource<Int, DiscussionEntity>

    @Query("DELETE from discussion")
    suspend fun deleteAllDiscussion()
}