package br.com.wms.link_shortener.domain.repository

import br.com.wms.link_shortener.data.model.CreateAliasDTO
import br.com.wms.link_shortener.data.model.CreateAliasRequest

internal interface LinkShortenerRepository {
    suspend fun createAlias(createAliasRequest: CreateAliasRequest): CreateAliasDTO
}
