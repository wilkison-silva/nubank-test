package br.com.wms.linkshortener.presentation.screen.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel

internal class LinkShortenerViewModel : ViewModel() {

    fun reduceUserAction(action: Actions) {
        when (action) {
            Actions.OnGenerateAliasClick -> {

            }
            is Actions.OnOpenAliasClick -> {

            }
        }
    }

    sealed class Actions {
        data object OnGenerateAliasClick : Actions()
        data class OnOpenAliasClick(val aliasUrl: String) : Actions()
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
