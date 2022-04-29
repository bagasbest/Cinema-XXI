package com.project.cinema

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
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
            intent.putExtra(MovieAddEditActivity.OPTION ,"edit")
            intent.putExtra(MovieAddEditActivity.EXTRA_DATA, model)
            startActivity(intent)
        }

        binding?.delete?.setOnClickListener {
            showAlertDialogDelete()
        }

    }

    private fun showAlertDialogDelete() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Menghapus Movie")
            .setMessage("Apakah anda yakin ingin menghapus Movie ini ?")
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("YA") { dialogInterface, _ ->
                dialogInterface.dismiss()

                val mProgressDialog = ProgressDialog(this)
                mProgressDialog.setMessage("Mohon tunggu hingga proses selesai...")
                mProgressDialog.setCanceledOnTouchOutside(false)
                mProgressDialog.show()

                FirebaseFirestore
                    .getInstance()
                    .collection("movie")
                    .document(model?.uid!!)
                    .delete()
                    .addOnCompleteListener {
                      if(it.isSuccessful) {
                          Toast.makeText(this, "Berhasil menghapus movie", Toast.LENGTH_SHORT).show()
                          val intent = Intent(this, MainActivity::class.java)
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                          startActivity(intent)
                      } else {
                          Toast.makeText(this, "Gagal menghapus movie, pastikan koneksi internet anda stabil", Toast.LENGTH_SHORT).show()
                      }
                    }
            }
            .setNegativeButton("TIDAK", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}