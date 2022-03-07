package com.example.androidtask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidtask.data.model.Image
import com.example.androidtask.data.model.ImageList
import com.example.androidtask.utils.Constants

@Entity(tableName = Constants.TABLE_IMAGES)
data class ImagesEntity(

    @PrimaryKey
    val id: Long,
    val tags: String,
    val previewURL: String,
    val largeImageURL: String,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val views: Long,
    val user: String,
    val keyword:String
)

fun ImagesEntity.toImageModel(): Image {
    return Image(
        id = this.id,
        tags = this.tags,
        previewURL = this.previewURL,
        largeImageURL = this.largeImageURL,
        downloads = this.downloads,
        likes = this.likes,
        comments = this.comments,
        views = this.views,
        user = this.user
    )
}

