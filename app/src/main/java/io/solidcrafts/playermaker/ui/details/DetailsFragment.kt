package io.solidcrafts.playermaker.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.solidcrafts.playermaker.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.movie = DetailsFragmentArgs.fromBundle(requireArguments()).movie
        return binding.root
    }
}