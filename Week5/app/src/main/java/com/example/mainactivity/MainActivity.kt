package com.example.mainactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

@SuppressLint("SetTextI18n")

class MainActivity : AppCompatActivity(){
    lateinit var mListener: OnDataToFragmentListener
    lateinit var mListenerTopRate: OnDataToFragmentListener

    var movies: ArrayList<Movie> = ArrayList()
    var moviesTopRate: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDataMovie()
        getDataTopRate()

        addFirstFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun addFirstFragment() {
        val nowPlayingFragment = NowPlayingFragment()
        mListener = nowPlayingFragment
        mListener.sendDataToFragment(movies)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.flContainer, nowPlayingFragment)
        fragmentTransaction.commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.top_rate -> {
                val topRateFragment = TopRateFragment()
                mListenerTopRate = topRateFragment
                mListenerTopRate.sendDataToFragment(moviesTopRate)
                openFragment(topRateFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.now_playing -> {
                val nowPlayingFragment = NowPlayingFragment()
                mListener = nowPlayingFragment
                mListener.sendDataToFragment(movies)
                openFragment(nowPlayingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.search_movie -> {
                val intent = Intent(this, SearchMovieActivity:: class.java)
                startActivity(intent)
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getDataMovie(){
        pbLoading.visibility = View.VISIBLE
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/now_playing?api_key=7519cb3f829ecd53bd9b7007076dbe23")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        pbLoading.visibility = View.GONE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()!!.string()
                    val jsonObject = JSONObject(json)
                    val jsonDataMovie = jsonObject.get("results").toString()

                    val collectionType = object : TypeToken<Collection<Movie>>(){}.type

                    movies = Gson().fromJson(jsonDataMovie, collectionType)

                    runOnUiThread {
                        pbLoading.visibility = View.GONE
                    }
                }

            })
    }

    private fun getDataTopRate() {
        pbLoading.visibility = View.VISIBLE
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/top_rated?api_key=7519cb3f829ecd53bd9b7007076dbe23")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        pbLoading.visibility = View.GONE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()!!.string()
                    val jsonObject = JSONObject(json)
                    val jsonDataMovie = jsonObject.get("results").toString()

                    val collectionType = object : TypeToken<Collection<Movie>>(){}.type

                    moviesTopRate = Gson().fromJson(jsonDataMovie, collectionType)

                    runOnUiThread {
                        pbLoading.visibility = View.GONE
                    }
                }
            })
    }
}

interface OnDataToFragmentListener {
    fun sendDataToFragment(movies: ArrayList<Movie>)
}