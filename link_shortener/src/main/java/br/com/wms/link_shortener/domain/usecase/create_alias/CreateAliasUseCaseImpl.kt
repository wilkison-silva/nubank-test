package br.com.wms.link_shortener.domain.usecase.create_alias

import br.com.wms.link_shortener.data.model.CreateAliasRequest
import br.com.wms.link_shortener.domain.extensions.toShortenedLink
import br.com.wms.link_shortener.domain.repository.LinkShortenerRepository

internal class CreateAliasUseCaseImpl(
    private val repository: LinkShortenerRepository,
) : CreateAliasUseCase {
    override suspend fun invoke(url: String): CreateAliasUseCase.Result =
        try {
            val request = CreateAliasRequest(url)
            val shortenedLink = repository.createAlias(request).toShortenedLink()
            CreateAliasUseCase.Result.Success(shortenedLink)
        } catch (exception: Exception) {
            CreateAliasUseCase.Result.Error
        }
}
