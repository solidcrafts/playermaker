package io.solidcrafts.playermaker

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.solidcrafts.playermaker.util.LoadingStatus

@BindingAdapter(value = ["movieDbApiPosterPath", "movieDbApiPosterSize"])
fun bindMovieDbImage(imgView: ImageView, path: String?, size: Int? = 200) {
    path?.let {

        val imgUri =
            "https://image.tmdb.org/t/p/w${size}/${it}"
                .toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("loadingStatus")
fun bindProgressBar(progressBar: ProgressBar, status: LoadingStatus?) = when (status) {
    LoadingStatus.ERROR -> progressBar.visibility = View.GONE
    LoadingStatus.DONE -> progressBar.visibility = View.GONE
    else -> progressBar.visibility = View.VISIBLE
}