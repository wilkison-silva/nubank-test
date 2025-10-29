package br.com.wms.linkshortener.data.repository

import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.data.model.CreateAliasResponse
import br.com.wms.linkshortener.data.model.GetUrlResponse
import br.com.wms.linkshortener.domain.repository.LinkShortenerRepository

internal class LinkShortenerRepositoryImpl(
    private val serviceApi: LinkShortenerServiceApi,
) : LinkShortenerRepository {
    override suspend fun createAlias(createAliasRequest: CreateAliasRequest): CreateAliasResponse {
        return serviceApi.createAlias(createAliasRequest)
    }

    override suspend fun getUrlFromAlias(alias: String): GetUrlResponse {
        return serviceApi.getUrlFromAlias(alias)
    }

}