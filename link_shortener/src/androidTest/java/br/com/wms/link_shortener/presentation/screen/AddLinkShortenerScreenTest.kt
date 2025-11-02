package br.com.wms.link_shortener.presentation.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import br.com.wms.link_shortener.di.loadLinkShortenerModules
import br.com.wms.link_shortener.domain.model.ShortenedLink
import br.com.wms.link_shortener.domain.usecase.create_alias.CreateAliasUseCase
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

internal class AddLinkShortenerScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    private fun setupKoin(result: CreateAliasUseCase.Result) {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            allowOverride(true)
            loadLinkShortenerModules()
            val testModule = module {
                single<CreateAliasUseCase> {
                    object : CreateAliasUseCase {
                        override suspend fun invoke(url: String): CreateAliasUseCase.Result = result
                    }
                }
            }
            loadKoinModules(testModule)
        }
    }

    @Test
    fun button_is_disabled_when_input_is_empty() {
        setupKoin(CreateAliasUseCase.Result.Error)
        composeRule.setContent {
            MaterialTheme {
                AddLinkShortenerScreen()
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val okText = context.getString(br.com.wms.link_shortener.R.string.label_ok)

        composeRule.onNodeWithText(okText).assertIsNotEnabled()
    }

    @Test
    fun invalid_url_shows_error_supporting_text() {
        setupKoin(CreateAliasUseCase.Result.Error)
        composeRule.setContent {
            MaterialTheme {
                AddLinkShortenerScreen()
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val okText = context.getString(br.com.wms.link_shortener.R.string.label_ok)
        val errorText = context.getString(br.com.wms.link_shortener.R.string.url_error)

        composeRule.onNode(hasSetTextAction()).performTextInput("invalid")
        composeRule.onNodeWithText(okText).assertIsEnabled().performClick()
        composeRule.onNodeWithText(errorText).assertIsDisplayed()
    }

    @Test
    fun valid_url_success_displays_item_and_clears_input() {
        val successResult = CreateAliasUseCase.Result.Success(
            ShortenedLink(
                alias = "alias123",
                links = ShortenedLink.Links(
                    originalUrl = "https://example.com",
                    short = "https://sho.rt/alias123",
                ),
            ),
        )
        setupKoin(successResult)

        composeRule.setContent {
            MaterialTheme {
                AddLinkShortenerScreen()
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val okText = context.getString(br.com.wms.link_shortener.R.string.label_ok)

        composeRule.onNode(hasSetTextAction()).performTextInput("https://example.com")
        composeRule.onNodeWithText(okText).assertIsEnabled().performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("alias123").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("alias123").assertIsDisplayed()
        composeRule.onNodeWithText("https://example.com").assertIsDisplayed()
        composeRule.onNodeWithText("https://sho.rt/alias123").assertIsDisplayed()
        composeRule.onNodeWithText(okText).assertIsNotEnabled()
    }

    @Test
    fun valid_url_error_displays_snackbar_message() {
        setupKoin(CreateAliasUseCase.Result.Error)

        composeRule.setContent {
            MaterialTheme {
                AddLinkShortenerScreen()
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val okText = context.getString(br.com.wms.link_shortener.R.string.label_ok)
        val errorSnack = context.getString(br.com.wms.link_shortener.R.string.error_create_shortened_url)

        composeRule.onNode(hasSetTextAction()).performTextInput("https://example.com")
        composeRule.onNodeWithText(okText).assertIsEnabled().performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText(errorSnack).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText(errorSnack).assertIsDisplayed()
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
