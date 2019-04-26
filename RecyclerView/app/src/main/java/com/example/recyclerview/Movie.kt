package com.hnam.recyclerview

//vote_count,id,video,vote_average,title,popularity,poster_path,original_language,original_title,genre_ids,backdrop_path,adult,overview,release_date
data class Movie(val title: String, val overview: String, val vote_average: Float, val vote_count: Int, val video: Boolean, val poster_path: String, val release_date: String)