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

        binding.recyclerView.adapter = MainVerticalAdapter(
            getCurrentScrollStateHolder(savedInstanceState),
            MovieClickedListener {
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

    private fun getCurrentScrollStateHolder(savedInstanceState: Bundle?): ScrollStateHolder {
        val scrollStateHolder: ScrollStateHolder
        when {
            savedInstanceState != null -> scrollStateHolder =
                ScrollStateHolder(savedInstanceState).also { viewModel.scrollStateHolder = it }

            viewModel.scrollStateHolder != null -> scrollStateHolder = viewModel.scrollStateHolder!!

            else -> scrollStateHolder =
                ScrollStateHolder(null).also { viewModel.scrollStateHolder = it }
        }
        return scrollStateHolder
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.scrollStateHolder?.onSaveInstanceState(outState)
    }
}