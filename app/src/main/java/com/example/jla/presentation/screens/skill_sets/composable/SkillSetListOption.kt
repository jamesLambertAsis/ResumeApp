package com.example.jla.presentation.screens.skill_sets.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jla.presentation.utils.SkillSet

@Composable
fun SkillSetListOption() {
    val skillSetList = listOf(SkillSet.ANDROID_DEVELOPMENT, SkillSet.WEB_DEVELOPMENT, SkillSet.DATABASE, SkillSet.TOOLS)
    val selected = remember { mutableStateOf("") }
    skillSetList.forEach { item ->
        Spacer(Modifier.height(26.dp))
        SkillSetItem(text = item, isSelected = selected.value == item) {
            selected.value = if (selected.value == item) "" else item
        }
    }
}