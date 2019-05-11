package com.example.mainactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.MovementMethod
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        getData()
    }

    private fun getData() {
        val data = intent.extras
        val movie = data.getParcelable<MovieModel>(MOVIE_MODEL_KEY)

        if (data != null) {
            tv_title.text = movie.title
            tv_overview.text = movie.overview
            tv_release_date.text = "Release date: " + movie.release_date

            Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
            .centerCrop()
            .override(800,1000)
            .into(img_movie)

            rating_bar.rating = movie.vote_average/2
            if (movie.video)
                img_play.visibility = View.VISIBLE
            else
                img_play.visibility = View.INVISIBLE
        }
    }
}
