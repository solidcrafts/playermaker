package io.solidcrafts.playermaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.solidcrafts.playermaker.databinding.MoviesRowBinding
import io.solidcrafts.playermaker.domain.MoviesRow

class MainVerticalAdapter(private val clickedListener: MovieClickedListener) :
    ListAdapter<MoviesRow, RowItemViewHolder>(RowDiffCallback()) {
    private val nestedAdapters = arrayListOf<NestedHorizontalAdapter>()

    fun submitChanges(data: List<MoviesRow>) {
        submitList(data)
        nestedAdapters.forEachIndexed { index, nestedAdapter ->
            nestedAdapter.submitList(data[index].data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MoviesRowBinding.inflate(layoutInflater, parent, false).apply {
            adapter = NestedHorizontalAdapter(clickedListener)
            nestedAdapters.add(adapter as NestedHorizontalAdapter);
        }
        return RowItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RowItemViewHolder(private val binding: MoviesRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MoviesRow) {
        binding.adapter?.submitList(item.data)
        binding.categorizedMovies = item
        binding.executePendingBindings()
    }
}

class RowDiffCallback : DiffUtil.ItemCallback<MoviesRow>() {
    override fun areItemsTheSame(oldItem: MoviesRow, newItem: MoviesRow): Boolean {
        return oldItem.tag == newItem.tag
    }

    override fun areContentsTheSame(
        oldItem: MoviesRow,
        newItem: MoviesRow
    ): Boolean {
        return oldItem.data == newItem.data
    }
}