package br.com.wms.linkshortener.domain.usecase.create_alias

import br.com.wms.linkshortener.domain.extensions.toShortenedLink
import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.domain.repository.LinkShortenerRepository

internal class CreateAliasUseCaseImpl(
    private val repository: LinkShortenerRepository,
) : CreateAliasUseCase {
    override suspend fun invoke(url: String): CreateAliasUseCase.Result {
        return try {
            val request = CreateAliasRequest(url)
            val shortenedLink = repository.createAlias(request).toShortenedLink()
            CreateAliasUseCase.Result.Success(shortenedLink)
        } catch (exception: Exception) {
            exception.printStackTrace()
            CreateAliasUseCase.Result.Error
        }
    }
}