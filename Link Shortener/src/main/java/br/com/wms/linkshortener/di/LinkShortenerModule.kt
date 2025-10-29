package br.com.wms.linkshortener.di

import br.com.wms.linkshortener.presentation.screen.viewmodel.LinkShortenerViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private object LinkShortenerModule {
    val data =
        module {

        }
    val domain =
        module {

        }
    val presentation =
        module {
            viewModel {
                LinkShortenerViewModel()
            }
        }
}

fun loadLinkShortenerModules() {
    loadKoinModules(
        listOf(
            LinkShortenerModule.data,
            LinkShortenerModule.presentation,
            LinkShortenerModule.presentation,
        ),
    )
}