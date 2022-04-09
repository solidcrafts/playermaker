package io.solidcrafts.playermaker.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.solidcrafts.playermaker.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(
            this, MainFragmentViewModel.Factory(requireActivity().application)
        )[MainFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerView.adapter = MainVerticalAdapter(MovieClickedListener {
            viewModel.notifyMovieClicked(it)
        })

        viewModel.observableMovieRows().observe(viewLifecycleOwner) { data ->
            (binding.recyclerView.adapter as MainVerticalAdapter).submitChanges(data)
        }

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner) { selectedMovie ->
            if (selectedMovie != null) {
                Log.i("Nav", "Navigate to ${selectedMovie.title}")
                viewModel.navigateToSelectedMovieComplete()
            }
        }

        return binding.root
    }
}