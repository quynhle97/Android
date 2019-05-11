package com.example.mainactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_search_movie.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView

@SuppressLint("SetTextI18n")

class SearchMovieActivity : AppCompatActivity() {
    var movies: ArrayList<Movie> = ArrayList()
    lateinit var movieAdapter: MovieAdapter
    var tmp: String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        val edt_search_movie: EditText = findViewById(R.id.edt_search_movie)
        val btn_search_movie: Button = findViewById(R.id.btn_search_movie)

        btn_search_movie.setOnClickListener {
            getDataMovie(edt_search_movie.text.toString())
            rcv_result_movies.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
            movieAdapter = MovieAdapter(movies, this)
            rcv_result_movies.adapter = movieAdapter
            movieAdapter.setListener(movieItemClickListener)
        }
    }

    private val movieItemClickListener = object: MovieItemClickListener {
        override fun onItemCLicked(position: Int) {
            val intent = Intent(this@SearchMovieActivity, DetailsActivity:: class.java)
            val item = movies[position]
            intent.putExtra(MOVIE_MODEL_KEY, MovieModel(item.title, item.overview, item.vote_average, item.vote_count, item.video, item.poster_path, item.release_date))
            startActivity(intent)
        }

        override fun onItemLongCLicked(position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private fun getDataMovie (movie_name: String){
        pbLoading3.visibility = View.VISIBLE
        val url: String =
            "https://api.themoviedb.org/3/search/movie?api_key=7519cb3f829ecd53bd9b7007076dbe23&query=$movie_name"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        pbLoading3.visibility = View.GONE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()!!.string()
                    val jsonObject = JSONObject(json)
                    val jsonDataMovie = jsonObject.get("results").toString()

                    val collectionType = object : TypeToken<Collection<Movie>>(){}.type
                    tmp = url
                    movies = Gson().fromJson(jsonDataMovie, collectionType)

                    runOnUiThread {
                        pbLoading3.visibility = View.GONE
                    }
                }
            })
    }
}
