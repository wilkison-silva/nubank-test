package br.com.wms.linkshortener.domain.usecase.create_alias

import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.domain.repository.LinkShortenerRepository

internal class CreateAliasUseCaseImpl(
    private val repository: LinkShortenerRepository,
) : CreateAliasUseCase {
    override suspend fun invoke(url: String): CreateAliasUseCase.Result {
        return try {
            val request = CreateAliasRequest(url)
            val response = repository.createAlias(request)
            CreateAliasUseCase.Result.Success(alias = response.urlAlias)
        } catch (exception: Exception) {
            exception.printStackTrace()
            CreateAliasUseCase.Result.Error
        }
    }
}