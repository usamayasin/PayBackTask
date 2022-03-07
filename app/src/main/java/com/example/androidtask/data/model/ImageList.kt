package com.example.androidtask.data.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.example.androidtask.data.local.entity.ImagesEntity
import kotlinx.android.parcel.RawValue

@Parcelize
data class ImageList(
    val total: Int,
    val totalHits: Int,
    var hits:  @RawValue List<ImagesEntity> = arrayListOf()
) : Parcelable

fun ImageList.toDataBaseModel(keyword:String): List<ImagesEntity> {
    val list: MutableList<ImagesEntity> = ArrayList()
    hits.map {
        ImagesEntity(
            id = it.id,
            tags = it.tags?:"",
            previewURL = it.previewURL?:"",
            largeImageURL = it.largeImageURL?:"",
            downloads = it.downloads,
            likes = it.likes,
            comments = it.comments,
            views = it.views,
            user = it.user?:"",
            keyword = keyword
        )
    }.run {
        list.addAll(this)
    }
    return list
}