package com.codepath.campgrounds

import java.io.Serializable

// Data class representing a campground
data class Campground(
    val name: String,
    val location: String,
    val description: String,
    val imageUrl: String
) : Serializable
