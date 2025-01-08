package com.example.jla.core

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BaseViewModel: ViewModel() {

    val db = Firebase.firestore

    val user = hashMapOf(
        "first" to "james",
        "last" to "pogi",
        "sex" to "12hrs"
    )

    fun addUser() {
        db.collection("Users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("xxx-->", "Home: id = ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("xxx-->", "Home error: $e")
            }
    }

    fun getUser() {
        db.collection("Users")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d("xxx-->", "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->

                Log.d("xxx-->", "error: $exception")

            }
    }

}