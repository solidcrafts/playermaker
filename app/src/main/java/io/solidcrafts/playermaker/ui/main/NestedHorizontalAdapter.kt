package io.solidcrafts.playermaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.solidcrafts.playermaker.databinding.MovieItemBinding
import io.solidcrafts.playermaker.domain.DomainMovie

class NestedHorizontalAdapter(private val clickedListener: MovieClickedListener) :
    ListAdapter<DomainMovie, MovieItemViewHolder>(MovieDiffCallback()) {

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

    fun bind(clickedListener: MovieClickedListener, item: DomainMovie) {
        binding.clickListener = clickedListener
        binding.movie = item
        binding.executePendingBindings()
    }
}

class MovieClickedListener(val listener: (movie: DomainMovie) -> Unit) {
    fun onClick(movie: DomainMovie) = listener(movie)
}

class MovieDiffCallback : DiffUtil.ItemCallback<DomainMovie>() {
    override fun areItemsTheSame(oldItem: DomainMovie, newItem: DomainMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DomainMovie, newItem: DomainMovie): Boolean {
        return oldItem == newItem;
    }
}