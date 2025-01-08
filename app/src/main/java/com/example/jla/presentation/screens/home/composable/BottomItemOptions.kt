package com.example.jla.presentation.screens.home.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomItemOptions(modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    val listState = rememberLazyListState()
    val itemList = listOf("Resume", "Skill Sets", "My Apps", "Info")
    val selectedOption = remember {
        mutableStateOf("")
    }

    Box{
        LazyRow(
            modifier = modifier,
            state = listState
        ) {
            item {
                itemList.forEachIndexed { index, item ->
                    BottomRowItem(text = item, isSelected = itemList[index] == selectedOption.value){
                        selectedOption.value = it
                        onClick(it)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                }
            }
        }
    }
}