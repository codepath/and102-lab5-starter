package com.codepath.campgrounds

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "CampgroundDetailActivity"
const val CAMPGROUND_EXTRA = "CAMPGROUND_EXTRA"

class CampgroundDetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Find the views for the screen
        imageView = findViewById(R.id.campgroundImage)
        nameTextView = findViewById(R.id.campgroundName)
        locationTextView = findViewById(R.id.campgroundLocation)
        descriptionTextView = findViewById(R.id.campgroundDescription)

        // Get the extra from the Intent
        val campground = intent.getSerializableExtra(CAMPGROUND_EXTRA) as Campground

        // Set the name, location, and description information
        nameTextView.text = campground.name
        locationTextView.text = campground.location
        descriptionTextView.text = campground.description

        // Load the image using Glide
        Glide.with(this)
            .load(campground.imageUrl)
            .into(imageView)
    }
}
