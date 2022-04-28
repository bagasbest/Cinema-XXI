package com.project.cinema

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MovieViewModel :  ViewModel() {

    private val movieList = MutableLiveData<ArrayList<MovieModel>>()
    private val listData = ArrayList<MovieModel>()
    private val TAG = MovieViewModel::class.java.simpleName

    fun setListMovie() {
        listData.clear()


        try {
            FirebaseFirestore.getInstance().collection("movie")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val model = MovieModel()
                     model.cast = "" + document.data["cast"]
                     model.description = "" + document.data["description"]
                     model.director = "" + document.data["director"]
                     model.distributor = "" + document.data["distributor"]
                     model.duration = "" + document.data["duration"]
                     model.genre = "" + document.data["genre"]
                     model.image = "" + document.data["image"]
                     model.producer = "" + document.data["producer"]
                     model.rating = "" + document.data["rating"]
                     model.theater = "" + document.data["theater"]
                     model.title = "" + document.data["title"]
                     model.website = "" + document.data["website"]
                     model.writer = "" + document.data["writer"]
                     model.uid = "" + document.data["uid"]

                        listData.add(model)
                    }
                    movieList.postValue(listData)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    fun getMovie() : LiveData<ArrayList<MovieModel>> {
        return movieList
    }

}