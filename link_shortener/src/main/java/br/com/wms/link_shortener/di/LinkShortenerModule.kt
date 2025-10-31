package br.com.wms.link_shortener.di

 import br.com.wms.link_shortener.BuildConfig
import br.com.wms.link_shortener.data.repository.LinkShortenerRepositoryImpl
import br.com.wms.link_shortener.data.repository.LinkShortenerServiceApi
import br.com.wms.link_shortener.domain.repository.LinkShortenerRepository
import br.com.wms.link_shortener.domain.usecase.create_alias.CreateAliasUseCase
import br.com.wms.link_shortener.domain.usecase.create_alias.CreateAliasUseCaseImpl
import br.com.wms.link_shortener.presentation.screen.viewmodel.LinkShortenerViewModel
import okhttp3.OkHttpClient
 import okhttp3.logging.HttpLoggingInterceptor
 import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private object LinkShortenerModule {
    val data =
        module {
            val baseUrl = "https://url-shortener-server.onrender.com"

            single<OkHttpClient> {
                val client = OkHttpClient.Builder()
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
                client.build()
            }
            single<Retrofit> {
                Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .client(get<OkHttpClient>())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            single<LinkShortenerServiceApi> {
                get<Retrofit>().create(LinkShortenerServiceApi::class.java)
            }
            factory<LinkShortenerRepository> {
                LinkShortenerRepositoryImpl(
                    serviceApi = get(),
                )
            }
        }
    val domain =
        module {
            factory<CreateAliasUseCase> {
                CreateAliasUseCaseImpl(
                    repository = get(),
                )
            }
        }
    val presentation =
        module {
            viewModel {
                LinkShortenerViewModel(
                    createAliasUseCase = get(),
                )
            }
        }
}

fun loadLinkShortenerModules() {
    loadKoinModules(
        listOf(
            LinkShortenerModule.data,
            LinkShortenerModule.domain,
            LinkShortenerModule.presentation,
        ),
    )
}
