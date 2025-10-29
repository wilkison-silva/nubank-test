package br.com.wms.linkshortener.presentation.screen.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import br.com.wms.linkshortener.domain.usecase.create_alias.CreateAliasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class LinkShortenerViewModel(
    private val createAliasUseCase: CreateAliasUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun reduceUserAction(action: Actions) {
        when (action) {
            Actions.OnGenerateAliasClick -> {

            }

            is Actions.OnOpenAliasClick -> {

            }
        }
    }

    private fun createAlias(url: String) {

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
