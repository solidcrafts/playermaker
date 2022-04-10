package io.solidcrafts.playermaker.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.solidcrafts.playermaker.databinding.FragmentMainBinding
import io.solidcrafts.playermaker.util.ScrollStateHolder

class MainFragment : Fragment() {
    private lateinit var scrollStateHolder: ScrollStateHolder

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

        scrollStateHolder = ScrollStateHolder(savedInstanceState)
        binding.recyclerView.adapter = MainVerticalAdapter(scrollStateHolder, MovieClickedListener {
            viewModel.notifyMovieClicked(it)
        })

        viewModel.observableMovieRows().observe(viewLifecycleOwner) { data ->
            (binding.recyclerView.adapter as MainVerticalAdapter).submitChanges(data)
        }

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner) { selectedMovie ->
            if (selectedMovie != null) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(
                        selectedMovie
                    )
                )
                viewModel.navigateToSelectedMovieComplete()
            }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollStateHolder.onSaveInstanceState(outState)
    }
}