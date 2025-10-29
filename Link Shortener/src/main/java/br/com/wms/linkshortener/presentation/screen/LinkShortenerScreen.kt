package br.com.wms.linkshortener.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.wms.linkshortener.presentation.screen.viewmodel.LinkShortenerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddLinkShortenerScreen() {
    LinkShortenerScreen()
}

@Composable
private fun LinkShortenerScreen(
    viewModel: LinkShortenerViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    ScreenContent(
        uiState = uiState,
        sendUserAction = viewModel::reduceUserAction
    )
}

@Composable
private fun ScreenContent(
    uiState: LinkShortenerViewModel.UiState,
    sendUserAction: (LinkShortenerViewModel.Actions) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Você está na página de encurtar urls!",
            )
        }
    }
}