package br.com.wms.link_shortener.domain.usecase.create_alias

import br.com.wms.link_shortener.domain.model.ShortenedLink

internal interface CreateAliasUseCase {
    suspend operator fun invoke(url: String): Result

    sealed class Result {
        data class Success(
            val shortenedLink: ShortenedLink,
        ) : Result()

        data object Error : Result()
    }
}
