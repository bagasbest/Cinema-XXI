package com.project.cinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.cinema.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    private var binding: ActivityMovieDetailBinding? = null
    private var model: MovieModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        model = intent.getParcelableExtra(EXTRA_DATA)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}