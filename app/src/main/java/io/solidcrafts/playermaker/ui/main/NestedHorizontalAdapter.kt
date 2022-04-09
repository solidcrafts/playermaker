package io.solidcrafts.playermaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.solidcrafts.playermaker.databinding.MovieItemBinding
import io.solidcrafts.playermaker.domain.Movie

class NestedHorizontalAdapter(private val clickedListener: MovieClickedListener) :
    ListAdapter<Movie, MovieItemViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(clickedListener, getItem(position))
    }
}

class MovieItemViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(clickedListener: MovieClickedListener, item: Movie) {
        binding.clickListener = clickedListener
        binding.movie = item
        binding.executePendingBindings()
    }
}

class MovieClickedListener(val listener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = listener(movie)
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}