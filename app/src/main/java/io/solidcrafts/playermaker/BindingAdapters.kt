package io.solidcrafts.playermaker

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.solidcrafts.playermaker.util.LoadingStatus

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

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
fun bindStatus(imageView: ImageView, status: LoadingStatus?) = when (status) {
    LoadingStatus.LOADING -> {
        imageView.visibility = View.VISIBLE
        imageView.setImageResource(R.drawable.loading_animation)
    }
    LoadingStatus.ERROR -> {
        imageView.visibility = View.VISIBLE
        imageView.setImageResource(R.drawable.ic_connection_error)
    }
    LoadingStatus.DONE -> {
        imageView.visibility = View.GONE
    }
    else -> {}
}