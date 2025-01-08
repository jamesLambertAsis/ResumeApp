package com.example.jla.domain.model

import java.text.DateFormat

data class Chat(
    val timeSent: Long = 0,
    val userName: String = "",
    val chat: String = ""
) {

    fun getTimeSentDisplay(): String {
        var dateToDisplay = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(timeSent)

        if (DateFormat.getDateInstance().format(timeSent) == DateFormat.getDateInstance().format(System.currentTimeMillis())) {
            dateToDisplay = DateFormat.getTimeInstance(DateFormat.SHORT).format(timeSent)
        }

        return dateToDisplay
    }

}