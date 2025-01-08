package com.example.jla.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.jla.data.StringUtils
import com.example.jla.domain.model.User
import com.example.jla.domain.repository.UserRepository
import com.example.jla.presentation.screens.my_apps.utils.LogInResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(db: Firebase) : UserRepository {

    private val firestoreDb = db.firestore
    private val usersDocs = firestoreDb.collection("Users")

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addUser(user: User) {

        val newUser = User(
            mobileNo = StringUtils().encodeToBase64(user.mobileNo),
            userName = user.userName
        )
        usersDocs
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d("xxx-->", "Home: id = ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("xxx-->", "Home error: $e")
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun signUp(user: User): String {
        var result = LogInResult.SUCCESS

        for (item in getUserList()) {
            if (StringUtils().decodeFromBase64(item.mobileNo) == user.mobileNo) {
                result = LogInResult.MOBILE_ALREADY_EXIST
                break
            } else if (item.userName == user.userName) {
                result = LogInResult.USER_NAME_ALREADY_EXIST
                break
            }
        }

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun logIn(user: User): Boolean {
        var result = false

        for (item in getUserList()) {
            if (StringUtils().decodeFromBase64(item.mobileNo) == user.mobileNo && item.userName == user.userName) {
                result = true
                break
            }
        }

        return result
    }

    private suspend fun getUserList(): List<User> {

        val userList = mutableListOf<User>()
        var user: User

        try {
            val querySnapshot = usersDocs.get().await() // wait until feting is finish
            for (document in querySnapshot) {
                user = document.toObject(User::class.java) //map document to domain
                userList.add(user)
            }
            return userList
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }

    }

}