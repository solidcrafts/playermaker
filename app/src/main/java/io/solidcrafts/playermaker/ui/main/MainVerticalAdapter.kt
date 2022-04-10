package io.solidcrafts.playermaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.solidcrafts.playermaker.databinding.MoviesRowBinding
import io.solidcrafts.playermaker.domain.MoviesRow
import io.solidcrafts.playermaker.util.ScrollStateHolder
import java.util.HashMap

class MainVerticalAdapter(
    private val scrollStateHolder: ScrollStateHolder,
    private val clickedListener: MovieClickedListener
) :
    ListAdapter<MoviesRow, RowItemViewHolder>(RowDiffCallback()) {
    private var nestedAdaptersRegistry: HashMap<Int, NestedHorizontalAdapter> = hashMapOf()

    fun submitChanges(data: List<MoviesRow>) {
        submitList(data)
        for (nestedAdapter in nestedAdaptersRegistry) {
            nestedAdapter.value.submitList(data[nestedAdapter.key].data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MoviesRowBinding.inflate(layoutInflater, parent, false).apply {
            adapter = NestedHorizontalAdapter(clickedListener)
        }
        var viewHolder = RowItemViewHolder(scrollStateHolder, binding)
        viewHolder.onCreated()
        return viewHolder
    }

    override fun onBindViewHolder(holder: RowItemViewHolder, position: Int) {
        holder.bind(position, getItem(position), nestedAdaptersRegistry)
    }

    override fun onViewRecycled(holder: RowItemViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }
}

class RowItemViewHolder(
    private val scrollStateHolder: ScrollStateHolder,
    private val binding: MoviesRowBinding,
) :
    RecyclerView.ViewHolder(binding.root), ScrollStateHolder.ScrollStateKeyProvider {

    fun bind(index: Int, item: MoviesRow, registry: HashMap<Int, NestedHorizontalAdapter>) {
        registry[index] = binding.adapter as NestedHorizontalAdapter
        binding.adapter?.submitList(item.data)
        binding.categorizedMovies = item
        binding.executePendingBindings()
        onBound()
    }

    override fun getScrollStateKey(): String? = binding.categorizedMovies?.title()
    private fun onBound() = scrollStateHolder.restoreScrollState(binding.recyclerView, this);
    fun onRecycled() = scrollStateHolder.saveScrollState(binding.recyclerView, this)
    fun onCreated() = scrollStateHolder.setupRecyclerView(binding.recyclerView, this)
}

class RowDiffCallback : DiffUtil.ItemCallback<MoviesRow>() {
    override fun areItemsTheSame(oldItem: MoviesRow, newItem: MoviesRow): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MoviesRow,
        newItem: MoviesRow
    ): Boolean {
        return oldItem.data == newItem.data
    }
}