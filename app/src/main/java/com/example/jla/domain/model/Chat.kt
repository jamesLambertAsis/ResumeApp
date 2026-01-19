package com.example.jla.domain.model

import com.example.jla.presentation.screens.my_apps.utils.ChatUtils
import com.google.firebase.firestore.Exclude
import java.text.DateFormat
import java.util.UUID

data class Chat(
    val id: String = UUID.randomUUID().toString(),
    val timeSent: Long = System.currentTimeMillis(),
    val userName: String = ChatUtils.loggedInUserName,
    val sending: Boolean = true,
    val sent: Boolean? = null,
    val edited: Boolean = false,
    val chat: String = ""
) {

    @Exclude
    fun getTimeSentDisplay(): String {
        var dateToDisplay = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(timeSent)

        if (DateFormat.getDateInstance().format(timeSent) == DateFormat.getDateInstance().format(System.currentTimeMillis())) {
            dateToDisplay = DateFormat.getTimeInstance(DateFormat.SHORT).format(timeSent)
        }
        return dateToDisplay
    }

}