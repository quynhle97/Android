package com.hnam.recyclerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.xml.sax.Parser
import java.lang.StringBuilder

class MoviesActivity : AppCompatActivity() {

    var movies: ArrayList<Movie> = ArrayList()
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Movie Theater")
        addMovies()

        rvMovies.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        movieAdapter = MovieAdapter(movies, this)

        rvMovies.adapter = movieAdapter

        movieAdapter.setListener(movieItemClickListener)
    }

    private val movieItemClickListener = object: MovieItemClickListener {
        override fun onItemCLicked(position: Int) {
            val intent = Intent(this@MoviesActivity, DetailMovieActivity:: class.java)
            intent.putExtra(MOVIE_TITLE_KEY, movies[position].title)
            intent.putExtra(MOVIE_OVERVIEW_KEY, movies[position].overview)
            intent.putExtra(MOVIE_IMAGE_KEY, movies[position].poster_path)
            intent.putExtra(MOVIE_VOTE_COUNT_KEY, movies[position].vote_count)
            intent.putExtra(MOVIE_VIDEO_KEY, movies[position].video)
            intent.putExtra(MOVIE_VOTE_AVERAGE_KEY, movies[position].vote_average)
            intent.putExtra(MOVIE_RELEASE_DATE_KEY, movies[position].release_date)
            startActivity(intent)
        }

        override fun onItemLongCLicked(position: Int) {
            // Dialog
        }
    }

    private fun addMovies() {
        var jsonString = FakeService.getMovieRaw()
        val jsonObject: JSONObject = JSONObject(jsonString)
        val jsonMovieModel = jsonObject.get("results").toString()

        val collectionType = object :TypeToken<Collection<Movie>>(){}.type

        movies = Gson().fromJson(jsonMovieModel, collectionType)
    }
}
