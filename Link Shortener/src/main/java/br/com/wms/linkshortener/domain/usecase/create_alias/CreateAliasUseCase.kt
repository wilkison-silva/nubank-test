package br.com.wms.linkshortener.domain.usecase.create_alias

import br.com.wms.linkshortener.domain.model.ShortenedLink

internal interface CreateAliasUseCase {
    suspend operator fun invoke(url: String): Result

    sealed class Result {
        data class Success(
            val shortenedLink: ShortenedLink,
        ) : Result()

        data object Error : Result()
    }
}
