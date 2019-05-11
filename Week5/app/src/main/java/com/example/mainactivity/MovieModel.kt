package com.example.mainactivity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel (val title: String, val overview: String, val vote_average: Float, val vote_count: Int, val video: Boolean, val poster_path: String, val release_date: String):Parcelable