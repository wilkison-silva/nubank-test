package br.com.wms.linkshortener.domain.repository

import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.data.model.CreateAliasDTO

internal interface LinkShortenerRepository {
    suspend fun createAlias(createAliasRequest: CreateAliasRequest): CreateAliasDTO
}