package com.yahmedwiem.contacts

sealed class MainViewState {
    object Loading : MainViewState()
    data class Success(val data: List<ContactUiModel>) : MainViewState()
    data class Error(val message: String) : MainViewState()
}
