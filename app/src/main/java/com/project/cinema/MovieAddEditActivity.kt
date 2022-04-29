package com.project.cinema

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.project.cinema.databinding.ActivityMovieAddBinding

class MovieAddEditActivity : AppCompatActivity() {

    private var binding: ActivityMovieAddBinding? = null
    private val REQUEST_IMAGE_FROM_GALLERY = 1001
    private var image: String ?= null
    private var model: MovieModel? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieAddBinding.inflate(layoutInflater)
        setContentView(binding?.root)

       val option = intent.getStringExtra(OPTION)
        if(option == "add") {
            binding?.addOrEdit?.text = "Tambahkan Upcoming Movie"
        } else {
            binding?.addOrEdit?.text = "Edit Movie"
            model = intent.getParcelableExtra(EXTRA_DATA)

            image = model?.image
            Glide.with(this)
                .load(image)
                .into(binding!!.roundedImageView)

            binding?.title?.setText(model?.title)
            binding?.description?.setText(model?.description)
            binding?.rating?.setText(model?.rating)
            binding?.theater?.setText(model?.theater)
            binding?.duration?.setText(model?.duration)
            binding?.genre?.setText(model?.genre)
            binding?.producer?.setText(model?.producer)
            binding?.director?.setText(model?.director)
            binding?.writer?.setText(model?.writer)
            binding?.cast?.setText(model?.cast)
            binding?.distributor?.setText(model?.distributor)
            binding?.website?.setText(model?.website)
        }

        binding?.backButton?.setOnClickListener {
            onBackPressed()
        }

        binding?.imageAdd?.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .compress(1024)
                .start(REQUEST_IMAGE_FROM_GALLERY)
        }

        binding?.saveBtn?.setOnClickListener {
            formValidation(option)
        }

    }

    private fun formValidation(option: String?) {
        val title = binding?.title?.text.toString().trim()
        val description = binding?.description?.text.toString().trim()
        val rating = binding?.rating?.text.toString().trim()
        val theater = binding?.theater?.text.toString().trim()
        val duration = binding?.duration?.text.toString().trim()
        val genre = binding?.genre?.text.toString().trim()
        val produser = binding?.producer?.text.toString().trim()
        val director = binding?.director?.text.toString().trim()
        val writer = binding?.writer?.text.toString().trim()
        val cast = binding?.cast?.text.toString().trim()
        val distributor = binding?.distributor?.text.toString().trim()
        val website = binding?.website?.text.toString().trim()

        when {
            title.isEmpty() -> {
                Toast.makeText(this, "Judul movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            description.isEmpty() -> {
                Toast.makeText(this, "Deskripsi movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            rating.isEmpty() -> {
                Toast.makeText(this, "Rating movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            theater.isEmpty() -> {
                Toast.makeText(this, "Theater movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            duration.isEmpty() -> {
                Toast.makeText(this, "Duration movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            genre.isEmpty() -> {
                Toast.makeText(this, "Genre movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            produser.isEmpty() -> {
                Toast.makeText(this, "Produser movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            director.isEmpty() -> {
                Toast.makeText(this, "Director movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            writer.isEmpty() -> {
                Toast.makeText(this, "Writer movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            cast.isEmpty() -> {
                Toast.makeText(this, "Cast movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            distributor.isEmpty() -> {
                Toast.makeText(this, "Distributor movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            website.isEmpty() -> {
                Toast.makeText(this, "Website tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            image == null -> {
                Toast.makeText(this, "Cover movie tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else -> {

                binding?.progresBar?.visibility = View.VISIBLE

                if(option == "add") {
                    val uid = System.currentTimeMillis().toString()
                    val data = mapOf(
                        "title" to title,
                        "description" to description,
                        "rating" to rating,
                        "theater" to theater,
                        "duration" to duration,
                        "genre" to genre,
                        "producer" to produser,
                        "director" to director,
                        "writer" to writer,
                        "cast" to cast,
                        "distributor" to distributor,
                        "website" to website,
                        "image" to image,
                        "uid" to uid,
                    )

                    FirebaseFirestore
                        .getInstance()
                        .collection("movie")
                        .document(uid)
                        .set(data)
                        .addOnCompleteListener {
                            if(it.isSuccessful) {
                                binding?.progresBar?.visibility = View.GONE
                                showSuccessDialog("Sukses Menabah Movie", "Movie ini akan di tambahkan ke daftar upcoming")
                            } else {
                                binding?.progresBar?.visibility = View.GONE
                                showFailureDialog("Gagal Menabah Movie", "Ups, gagal mengunggah movie, silahkan periksa koneksi internet anda")
                            }
                        }

                } else {
                    val data = mapOf(
                        "title" to title,
                        "description" to description,
                        "rating" to rating,
                        "theater" to theater,
                        "duration" to duration,
                        "genre" to genre,
                        "producer" to produser,
                        "director" to director,
                        "writer" to writer,
                        "cast" to cast,
                        "distributor" to distributor,
                        "website" to website,
                        "image" to image,
                    )

                    FirebaseFirestore
                        .getInstance()
                        .collection("movie")
                        .document(model?.uid!!)
                        .update(data)
                        .addOnCompleteListener {
                            if(it.isSuccessful) {
                                binding?.progresBar?.visibility = View.GONE
                                showSuccessDialog("Sukses Memperbarui Movie", "Movie ini akan di perbarui ke daftar upcoming")
                            } else {
                                binding?.progresBar?.visibility = View.GONE
                                showFailureDialog("Gagal Memperbarui Movie", "Ups, gagal mengunggah movie, silahkan periksa koneksi internet anda")
                            }
                        }

                }

            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_FROM_GALLERY) {
                uploadImage(data?.data)
            }
        }
    }


    /// fungsi untuk mengupload foto kedalam cloud storage
    @SuppressLint("SetTextI18n")
    private fun uploadImage(data: Uri?) {
        val mStorageRef = FirebaseStorage.getInstance().reference
        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Mohon tunggu hingga proses selesai...")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
        val imageFileName = "cover/image_" + System.currentTimeMillis() + ".png"
        mStorageRef.child(imageFileName).putFile(data!!)
            .addOnSuccessListener {
                mStorageRef.child(imageFileName).downloadUrl
                    .addOnSuccessListener { uri: Uri ->
                        mProgressDialog.dismiss()
                        image = uri.toString()
                        Glide.with(this)
                            .load(image)
                            .into(binding!!.roundedImageView)
                    }
                    .addOnFailureListener { e: Exception ->
                        mProgressDialog.dismiss()
                        Toast.makeText(
                            this,
                            "Gagal mengunggah gambar",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("imageDp: ", e.toString())
                    }
            }
            .addOnFailureListener { e: Exception ->
                mProgressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Gagal mengunggah gambar",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.d("imageDp: ", e.toString())
            }
    }


    private fun showFailureDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun showSuccessDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val OPTION = "addEdit"
        const val EXTRA_DATA = "data"
    }
}