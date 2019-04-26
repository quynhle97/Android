package com.hnam.recyclerview

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movie.*
import android.widget.TextView



class DetailMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val data = intent.extras
        setTitle(data.getString(MOVIE_TITLE_KEY))
        getData()
    }

    private fun getData() {
        val data = intent.extras

        if (data != null) {
            val title = data.getString(MOVIE_TITLE_KEY)
            val overview = data.getString(MOVIE_OVERVIEW_KEY)
            val poster_path = data.getString(MOVIE_IMAGE_KEY)
            val vote_counter = data.getInt(MOVIE_VOTE_COUNT_KEY)
            val video = data.getBoolean(MOVIE_VIDEO_KEY)
            val release_date = data.getString(MOVIE_RELEASE_DATE_KEY)
            val vote_average = data.getFloat(MOVIE_VOTE_AVERAGE_KEY)

            tv_title.text = title
            tv_overview.text = overview
            tv_release_date.text = "Release date: " + release_date

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + poster_path)
                .centerCrop()
                .override(800,1000)
                .into(img_movie)

            rating_bar.rating = vote_average/2
            if (video)
                img_play.visibility = View.VISIBLE
            else
                img_play.visibility = View.INVISIBLE
        }
    }
}
