package br.com.wms.linkshortener.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.wms.linkshortener.R
import br.com.wms.linkshortener.presentation.extensions.ObserveEvents
import br.com.wms.linkshortener.presentation.screen.viewmodel.LinkShortenerViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddLinkShortenerScreen() {
    LinkShortenerScreen()
}

@Composable
private fun LinkShortenerScreen(viewModel: LinkShortenerViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveEvents(events = viewModel.events) { event ->
        when (event) {
            is LinkShortenerViewModel.Event.AliasError -> {
            }

            LinkShortenerViewModel.Event.OpenBrowser -> {
            }

            LinkShortenerViewModel.Event.ShortenerLinkError -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = "Erro ao criar url reduzida!",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    ScreenContent(
        uiState = uiState,
        sendUserAction = viewModel::reduceUserAction,
        snackBarHostState = snackBarHostState,
    )
}

@Composable
private fun ScreenContent(
    uiState: LinkShortenerViewModel.UiState,
    sendUserAction: (LinkShortenerViewModel.Actions) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .padding(
                            horizontal = 24.dp,
                            vertical = 12.dp,
                        ).fillMaxWidth()
                        .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                OutlinedTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    value = uiState.inputText,
                    onValueChange = {
                        sendUserAction(LinkShortenerViewModel.Actions.ChangedInputText(text = it))
                    },
                    label = {
                        Text(text = "Insira a url")
                    },
                    singleLine = true,
                )
                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(size = 12.dp),
                    onClick = {
                        sendUserAction(LinkShortenerViewModel.Actions.GenerateAliasClick)
                    },
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = "OK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            LazyColumn(
                modifier =
                    Modifier
                        .padding(top = 12.dp)
                        .fillMaxSize(),
            ) {
                items(
                    count = uiState.aliasList.count(),
                    key = { index ->
                        index
                    },
                    itemContent = { index ->
                        Column {
                            Row(
                                modifier = Modifier.padding(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = uiState.aliasList[index].alias,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_outline_arrow_forward_ios_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                        Text(
                            modifier =
                                Modifier
                                    .padding(horizontal = 24.dp)
                                    .fillMaxWidth(),
                            text = uiState.aliasList[index].originalUrl,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            modifier =
                                Modifier
                                    .padding(horizontal = 24.dp)
                                    .fillMaxWidth(),
                            text = uiState.aliasList[index].short,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Light,
                        )
                        if (index < uiState.aliasList.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 0.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                )
            }
        }
    }
}
