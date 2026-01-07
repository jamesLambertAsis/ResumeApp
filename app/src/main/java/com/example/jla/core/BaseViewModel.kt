package com.example.jla.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel<T>(
    initialState: T
): ViewModel() {

    protected val _state = MutableStateFlow(initialState)

    val state = _state.asStateFlow()

    fun run(task : suspend () -> Unit){
        viewModelScope.launch {
            task.invoke()
        }
    }
}