package com.example.buscadorpeliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.buscadorpeliculas.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(var items: List<Movie>, val onItemClick: (Int)-> Unit):
    Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val superhero = items[position]
        holder.render(superhero)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }

    }
    override fun getItemCount(): Int {
        return items.size

    }
    fun updateItems(items: List<Movie>){
        this.items = items
        notifyDataSetChanged()
    }
}

class MovieViewHolder(var binding: ItemMovieBinding): ViewHolder(binding.root){
    fun render(movie: Movie){
        binding.titleTextView.text = movie.Title
        binding.movieYearTextView.text = movie.Year
        Picasso.get().load(movie.Poster).into(binding.posterImageView)
    }
}