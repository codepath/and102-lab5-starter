package com.codepath.campgrounds

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val CAMPGROUND_EXTRA = "CAMPGROUND_EXTRA"
private const val TAG = "CampgroundAdapter"

class CampgroundAdapter(private val context: Context, private val campgrounds: List<Campground>) :
    RecyclerView.Adapter<CampgroundAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_campground, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val campground = campgrounds[position]
        holder.bind(campground)
    }

    override fun getItemCount() = campgrounds.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val imageView = itemView.findViewById<ImageView>(R.id.campgroundImage)
        private val nameTextView = itemView.findViewById<TextView>(R.id.campgroundName)
        private val locationTextView = itemView.findViewById<TextView>(R.id.campgroundLocation)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(campground: Campground) {
            nameTextView.text = campground.name
            locationTextView.text = campground.location

            Glide.with(context)
                .load(campground.imageUrl)
                .into(imageView)
        }

        override fun onClick(v: View?) {
            val campground = campgrounds[absoluteAdapterPosition]
            val intent = Intent(context, CampgroundDetailActivity::class.java)
            intent.putExtra(CAMPGROUND_EXTRA, campground)
            context.startActivity(intent)
        }
    }
}
