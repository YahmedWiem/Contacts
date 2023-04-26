package com.yahmedwiem.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yahmedwiem.contacts.domain.FetchLocalContactUseCase
import com.yahmedwiem.contacts.domain.FilterUseCase
import com.yahmedwiem.contacts.domain.toUi
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: FetchLocalContactUseCase,
    private val filterUseCase: FilterUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<MainViewState>(MainViewState.Loading)
    val viewState: LiveData<MainViewState> = _viewState

    fun fetchContact() {
        viewModelScope.launch {
            try {
                val result = useCase.execute()
                _viewState.value = MainViewState.Success(result.map { it.toUi() })
            } catch (ex: Exception) {
                _viewState.value = MainViewState.Error(ex.message.orEmpty())
            }
        }
    }

    fun filter(query: String) {
        viewModelScope.launch {
            _viewState.value = MainViewState.Loading
            if (query.isBlank())
                fetchContact()
            else {
                val result = filterUseCase.execute(query)
                _viewState.value = MainViewState.Success(result.map { it.toUi() })
            }
        }
    }

}