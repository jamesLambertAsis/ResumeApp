package com.example.jla.presentation.navigation.nav

sealed class NavEvent {

    object ToHomeScreen: NavEvent()
    object ToSkillSetsScreen: NavEvent()
    object ToResumeScreen: NavEvent()
    object ToMyApps: NavEvent()
    object ToInfoScreen: NavEvent()
    object ToChatScreen: NavEvent()
    object ToWebViewScreen: NavEvent()
    object ToMapScreen: NavEvent()

}