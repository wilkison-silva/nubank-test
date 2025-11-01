package br.com.wms.link_shortener.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.wms.link_shortener.R
import br.com.wms.link_shortener.presentation.Dimens
import br.com.wms.link_shortener.presentation.extensions.ObserveEvents
import br.com.wms.link_shortener.presentation.screen.viewmodel.LinkShortenerViewModel
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
    val context = LocalContext.current

    ObserveEvents(events = viewModel.events) { event ->
        when (event) {
            is LinkShortenerViewModel.Event.AliasError -> {
            }

            LinkShortenerViewModel.Event.OpenBrowser -> {
            }

            LinkShortenerViewModel.Event.ShortenerLinkError -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = context.getString(R.string.error_create_shortened_url),
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
            SnackbarHost(
                snackBarHostState,
                Modifier.padding(bottom = Dimens.Space.space_24dp),
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_12dp),
        ) {
            InputSections(
                uiState = uiState,
                sendUserAction = sendUserAction,
            )
            if (uiState.isLoading) {
                LoadingComponent()
            } else {
                ShortenedUrlsList(uiState)
            }
        }
    }
}

@Composable
private fun LoadingComponent() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            modifier =
                Modifier
                    .size(Dimens.Space.space_32dp)
                    .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun ShortenedUrlsList(uiState: LinkShortenerViewModel.UiState) {
    LazyColumn(
        modifier =
            Modifier
                .padding(top = Dimens.Space.space_12dp)
                .fillMaxSize(),
    ) {
        items(
            count = uiState.aliasList.count(),
            key = { index ->
                index
            },
            itemContent = { index ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_12dp),
                ) {
                    Row(
                        modifier =
                            Modifier.padding(
                                horizontal = Dimens.Space.space_24dp,
                                vertical = Dimens.Space.space_12dp,
                            ),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.Space.space_24dp),
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
                            .padding(horizontal = Dimens.Space.space_24dp)
                            .fillMaxWidth(),
                    text = uiState.aliasList[index].originalUrl,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    modifier =
                        Modifier
                            .padding(horizontal = Dimens.Space.space_24dp)
                            .fillMaxWidth(),
                    text = uiState.aliasList[index].short,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light,
                )
                if (index < uiState.aliasList.lastIndex) {
                    HorizontalDivider(
                        modifier =
                            Modifier
                                .padding(top = Dimens.Space.space_12dp)
                                .fillMaxWidth(),
                        thickness = Dimens.Thickness.thickness_0dp,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
        )
    }
}

@Composable
private fun InputSections(
    uiState: LinkShortenerViewModel.UiState,
    sendUserAction: (LinkShortenerViewModel.Actions) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .padding(
                    horizontal = Dimens.Space.space_24dp,
                    vertical = Dimens.Space.space_12dp,
                ).fillMaxWidth()
                .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space.space_24dp),
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
                Text(text = stringResource(R.string.insert_url))
            },
            singleLine = true,
            isError = uiState.inputUrlError,
            supportingText = {
                if (uiState.inputUrlError) {
                    Text(text = stringResource(id = R.string.url_error))
                }
            },
        )
        Button(
            modifier = Modifier,
            enabled = uiState.isButtonEnabled,
            shape = RoundedCornerShape(size = Dimens.Space.space_12dp),
            onClick = {
                sendUserAction(LinkShortenerViewModel.Actions.GenerateAliasClick)
            },
        ) {
            Text(
                modifier = Modifier.padding(vertical = Dimens.Space.space_4dp),
                text = stringResource(R.string.label_ok),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
