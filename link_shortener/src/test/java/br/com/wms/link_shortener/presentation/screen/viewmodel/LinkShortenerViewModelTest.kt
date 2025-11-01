package br.com.wms.link_shortener.presentation.screen.viewmodel

import app.cash.turbine.test
import br.com.wms.link_shortener.domain.model.ShortenedLink
import br.com.wms.link_shortener.domain.usecase.create_alias.CreateAliasUseCase
import br.com.wms.link_shortener.presentation.model.ShortenedLinkView
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LinkShortenerViewModelTest {
    private val createAliasUseCase: CreateAliasUseCase = mockk()
    private lateinit var viewModel: LinkShortenerViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LinkShortenerViewModel(createAliasUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `reduceUserAction WHEN action is ChangedInputText SHOULD update inputText`() =
        runTest {
            // GIVEN
            val newText = "https://google.com"

            // WHEN
            viewModel.reduceUserAction(LinkShortenerViewModel.Actions.ChangedInputText(newText))

            // THEN
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals(state.inputText, newText)
                assertTrue(state.isButtonEnabled)
            }
        }

    @Test
    fun `reduceUserAction WHEN action is GenerateAliasClick with valid url SHOULD update aliasList`() =
        runTest {
            // GIVEN
            val url = "https://google.com"
            val shortenedLink =
                ShortenedLink(
                    alias = "alias",
                    links =
                        ShortenedLink.Links(
                            originalUrl = "original",
                            short = "short",
                        ),
                )
            val shortenedLinkView =
                ShortenedLinkView(
                    alias = "alias",
                    originalUrl = "original",
                    short = "short",
                )
            coEvery { createAliasUseCase(url) } returns
                CreateAliasUseCase.Result.Success(
                    shortenedLink,
                )
            viewModel.reduceUserAction(LinkShortenerViewModel.Actions.ChangedInputText(url))

            // WHEN
            viewModel.reduceUserAction(LinkShortenerViewModel.Actions.GenerateAliasClick)
            advanceUntilIdle()

            // THEN
            viewModel.uiState.test {
                val state = awaitItem()
                assertTrue(state.aliasList.contains(shortenedLinkView))
                assertTrue(state.inputText.isEmpty())
                assertFalse(state.isLoading)
            }
        }

    @Test
    fun `reduceUserAction WHEN action is GenerateAliasClick with invalid url SHOULD update inputUrlError`() =
        runTest {
            // GIVEN
            val url = "invalid-url"
            viewModel.reduceUserAction(LinkShortenerViewModel.Actions.ChangedInputText(url))

            // WHEN
            viewModel.reduceUserAction(LinkShortenerViewModel.Actions.GenerateAliasClick)

            // THEN
            viewModel.uiState.test {
                val state = awaitItem()
                assertTrue(state.inputUrlError)
            }
        }

    @Test
    fun `reduceUserAction WHEN action is GenerateAliasClick and usecase fails SHOULD send ShortenerLinkError event`() =
        runTest {
            // GIVEN
            val url = "https://google.com"
            coEvery { createAliasUseCase(url) } returns CreateAliasUseCase.Result.Error
            viewModel.reduceUserAction(LinkShortenerViewModel.Actions.ChangedInputText(url))

            // WHEN
            viewModel.reduceUserAction(LinkShortenerViewModel.Actions.GenerateAliasClick)

            // THEN
            viewModel.events.test {
                val event = awaitItem()
                assertEquals(event, LinkShortenerViewModel.Event.ShortenerLinkError)
            }
            viewModel.uiState.test {
                val state = awaitItem()
                assertFalse(state.isLoading)
            }
        }
}
