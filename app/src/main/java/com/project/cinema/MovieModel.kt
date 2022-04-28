package com.project.cinema

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    var title: String? = null,
    var description: String? = null,
    var rating: String? = null,
    var theater: String? = null,
    var duration: String? = null,
    var genre: String? = null,
    var producer: String? = null,
    var director: String? = null,
    var writer: String? = null,
    var cast: String? = null,
    var distributor: String? = null,
    var website: String? = null,
    var image: String? = null,
    var uid: String? = null,
) : Parcelable