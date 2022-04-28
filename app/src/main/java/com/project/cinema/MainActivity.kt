package com.project.cinema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.cinema.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var adapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initRecyclerView()
        initViewModel()

        binding?.add?.setOnClickListener {
            val intent = Intent(this, MovieAddEditActivity::class.java)
            intent.putExtra(MovieAddEditActivity.OPTION ,"add")
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding?.rvMovie?.layoutManager = layoutManager
        adapter = MovieAdapter()
        binding?.rvMovie?.adapter = adapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        binding?.progresBar?.visibility = View.VISIBLE
        viewModel.setListMovie()
        viewModel.getMovie().observe(this) { movieList ->
            if (movieList.size > 0) {
                adapter?.setData(movieList)
                binding?.noData?.visibility = View.GONE
            } else {
                binding?.noData?.visibility = View.VISIBLE
            }
            binding!!.progresBar.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}