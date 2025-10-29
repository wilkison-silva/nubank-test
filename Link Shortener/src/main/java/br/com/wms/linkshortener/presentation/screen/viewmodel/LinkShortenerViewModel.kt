package br.com.wms.linkshortener.presentation.screen.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.wms.linkshortener.domain.usecase.create_alias.CreateAliasUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class LinkShortenerViewModel(
    private val createAliasUseCase: CreateAliasUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    fun reduceUserAction(action: Actions) {
        when (action) {
            Actions.GenerateAliasClick -> {
                createAlias(url = _uiState.value.inputText)
            }

            is Actions.OpenAliasClick -> {

            }

            is Actions.ChangedInputText -> {
                _uiState.value = _uiState.value.copy(
                    inputText = action.text
                )
            }
        }
    }

    private fun createAlias(url: String) {
        viewModelScope.launch {
            when (val result = createAliasUseCase(url)) {
                CreateAliasUseCase.Result.Error -> {
                    sendEvent(Event.ShortenerLinkError)
                }
                is CreateAliasUseCase.Result.Success -> {
                    val aliasList = _uiState.value.aliasList.toMutableList().apply {
                        add(result.alias)
                    }
                    _uiState.value = _uiState.value.copy(
                        inputText = "",
                        aliasList = aliasList
                    )
                }
            }
        }
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

    sealed class Actions {
        data object GenerateAliasClick : Actions()
        data class OpenAliasClick(val aliasUrl: String) : Actions()
        data class ChangedInputText(val text: String) : Actions()
    }

    sealed class Event {
        data object OpenBrowser : Event()

        data class AliasError(@StringRes val errorMessage: Int) : Event()

        data object ShortenerLinkError : Event()
    }

    data class UiState(
        val isLoading: Boolean = false,
        val inputText: String = "",
        val aliasList: List<String> = listOf(),
    )
}
