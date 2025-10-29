package br.com.wms.linkshortener.domain.repository

import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.data.model.CreateAliasResponse
import br.com.wms.linkshortener.data.model.GetUrlResponse

internal interface LinkShortenerRepository {
    suspend fun createAlias(createAliasRequest: CreateAliasRequest): CreateAliasResponse

    suspend fun getUrlFromAlias(alias: String): GetUrlResponse
}