package io.solidcrafts.playermaker.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainMovie(val id: Int, val name: String, val posterUrl: String) : Parcelable