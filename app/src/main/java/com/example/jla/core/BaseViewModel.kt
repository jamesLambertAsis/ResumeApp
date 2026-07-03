package com.example.jla.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

open class BaseViewModel<T>(
    val initialState: T
): ViewModel() {

    val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun setState(newState: T){
        _state.update {  newState }
    }

    fun resetState(){
        _state.value = initialState
    }

    fun run(task : suspend () -> Unit){
        viewModelScope.launch {
            task.invoke()
        }
    }

    fun runOnBackground(task : suspend () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            task.invoke()
        }
    }
}