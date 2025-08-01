package com.codepath.campgrounds

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.campgrounds.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "CampgroundsMain/"
private val PARKS_API_KEY = BuildConfig.API_KEY
private val CAMPGROUNDS_URL =
    "https://developer.nps.gov/api/v1/campgrounds?api_key=${PARKS_API_KEY}"

class MainActivity : AppCompatActivity() {
    private lateinit var campgroundsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val campgrounds = mutableListOf<Campground>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        campgroundsRecyclerView = findViewById(R.id.campgrounds)

        // TODO: Set up CampgroundAdapter with campgrounds
        val campgroundAdapter = CampgroundAdapter(this, campgrounds)
        campgroundsRecyclerView.adapter = campgroundAdapter

        campgroundsRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            campgroundsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client.get(CAMPGROUNDS_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch campgrounds: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched campgrounds: $json")
                try {
                    // TODO: Create the parsedJSON
                    val parsedJson = createJson().decodeFromString(
                        CampgroundResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    // TODO: Do something with the returned json (contains campground information)
                    parsedJson.data?.let { list ->
                        campgrounds.addAll(list)

                        // TODO: Notify the adapter that the dataset has changed
                        campgroundAdapter.notifyDataSetChanged()
                    }

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })
    }
}
