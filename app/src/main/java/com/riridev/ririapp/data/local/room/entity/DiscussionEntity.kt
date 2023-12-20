package com.riridev.ririapp.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discussion")
data class DiscussionEntity(
    @PrimaryKey
    val postId: String,
    val userProfileImage: String,
    val username: String,
    val imageUrl: String,
    val title: String,
    val content: String,
    @ColumnInfo(defaultValue = "false")
    var isLiked: Boolean = false,
)
