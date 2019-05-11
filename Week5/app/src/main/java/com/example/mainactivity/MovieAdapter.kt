package com.example.mainactivity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_now_playing.view.*

class MovieAdapter (var items: ArrayList<Movie>, val context: Context): RecyclerView.Adapter<MovieViewHolder>() {
    lateinit var mListener: MovieItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_now_playing, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        movieViewHolder.tvTitle.text = items[position].title
        movieViewHolder.tvDetail.text = items[position].overview
        // Glide
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/" + items[position].poster_path)
            .centerCrop()
            .override(430,600)
            .into(movieViewHolder.imgView)


        movieViewHolder.itemView.setOnClickListener{
            mListener.onItemCLicked(position)
        }

        movieViewHolder.itemView.setOnLongClickListener {
            mListener.onItemLongCLicked(position)
            true
        }
    }

    fun setListener(listener: MovieItemClickListener) {
        this.mListener = listener
    }

    fun setData(items: ArrayList<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var tvTitle = view.tv_title_film
    var imgView = view.img_film
    var tvDetail = view.tv_detail_film
}