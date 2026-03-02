package com.example.jla.domain.use_case.chat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import com.example.jla.presentation.screens.my_apps.chat.ChatViewModel
import kotlinx.coroutines.flow.Flow

class GetChats (
    private val chatRepository: ChatRepository
) {
    operator fun invoke(context: Context): Flow<TaskResult<List<Chat>>> {
        return chatRepository.getChats(isNetworkAvailable(context))
    }

    //used to update chat to match data
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}