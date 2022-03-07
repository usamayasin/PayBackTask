package com.example.androidtask.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val id: Long = -1,
    val previewHeight: Int = -1,
    val likes: Int = -1,
    val tags: String? = null,
    val webformatHeight: Int = -1,
    val views: Long = -1,
    val webformatWidth: Int = -1,
    val previewWidth: Int = -1,
    val comments: Int = -1,
    val downloads: Int = -1,
    val pageURL: String? = null,
    val previewURL: String? = null,
    val largeImageURL: String? = null,
    val webformatURL: String? = null,
    val imageWidth: Int = -1,
    val userId: Int = -1,
    val user: String? = null,
    val type: String? = null,
    val user_id: Int = -1,
    val userImageURL: String? = null,
    val imageHeight: Int = -1
) : Parcelable
