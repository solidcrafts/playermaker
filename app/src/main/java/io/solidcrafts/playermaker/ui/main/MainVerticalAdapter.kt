package io.solidcrafts.playermaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.solidcrafts.playermaker.databinding.MoviesRowBinding
import io.solidcrafts.playermaker.domain.CategorizedMovies

class MainVerticalAdapter() :
    ListAdapter<CategorizedMovies, RowItemViewHolder>(RowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MoviesRowBinding.inflate(layoutInflater, parent, false)
        return RowItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RowItemViewHolder(private val binding: MoviesRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategorizedMovies) {
        binding.executePendingBindings()
    }
}

class RowDiffCallback : DiffUtil.ItemCallback<CategorizedMovies>() {
    override fun areItemsTheSame(oldItem: CategorizedMovies, newItem: CategorizedMovies): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CategorizedMovies,
        newItem: CategorizedMovies
    ): Boolean {
        return oldItem == newItem;
    }
}