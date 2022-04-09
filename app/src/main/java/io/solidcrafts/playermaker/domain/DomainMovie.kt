package io.solidcrafts.playermaker.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainMovie(val id: Int, val title: String, val posterPath: String) : Parcelable