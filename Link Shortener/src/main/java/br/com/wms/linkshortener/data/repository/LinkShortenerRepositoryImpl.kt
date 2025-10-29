package br.com.wms.linkshortener.data.repository

import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.data.model.CreateAliasDTO
import br.com.wms.linkshortener.domain.repository.LinkShortenerRepository

internal class LinkShortenerRepositoryImpl(
    private val serviceApi: LinkShortenerServiceApi,
) : LinkShortenerRepository {
    override suspend fun createAlias(createAliasRequest: CreateAliasRequest): CreateAliasDTO {
        return serviceApi.createAlias(createAliasRequest)
    }
}