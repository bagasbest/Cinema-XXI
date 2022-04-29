package com.project.cinema

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.project.cinema.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    private var binding: ActivityMovieDetailBinding? = null
    private var model: MovieModel? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        model = intent.getParcelableExtra(EXTRA_DATA)

        Glide.with(this)
            .load(model?.image)
            .into(binding!!.image)

        binding?.title?.text = model?.title
        binding?.rating?.text = model?.rating
        binding?.genre?.text = model?.genre
        binding?.duration?.text = "${model?.duration} Minutes"
        binding?.theater?.text = model?.theater
        binding?.description?.text = model?.description
        binding?.producer?.text = model?.producer
        binding?.director?.text = model?.director
        binding?.writer?.text = model?.writer
        binding?.cast?.text = model?.cast
        binding?.distributor?.text = model?.distributor
        binding?.website?.text = model?.website

        binding?.backButton?.setOnClickListener {
            onBackPressed()
        }

        binding?.button?.setOnClickListener {
            Toast.makeText(this, "Pemutaran Film ini besok, jangan sampai ketinggalan", Toast.LENGTH_SHORT).show()
        }

        binding?.button2?.setOnClickListener {
            Toast.makeText(this, "Anda berhasil membeli tiket film", Toast.LENGTH_SHORT).show()
        }

        binding?.button3?.setOnClickListener {
            Toast.makeText(this, "Anda telah melihat trailer film ini", Toast.LENGTH_SHORT).show()
        }

        binding?.edit?.setOnClickListener {
            val intent = Intent(this, MovieAddEditActivity::class.java)
            intent.putExtra(MovieAddEditActivity.OPTION ,"add")
            intent.putExtra(MovieAddEditActivity.EXTRA_DATA, model)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}