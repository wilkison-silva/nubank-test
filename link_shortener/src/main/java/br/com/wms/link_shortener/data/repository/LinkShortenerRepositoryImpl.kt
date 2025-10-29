package br.com.wms.link_shortener.data.repository

import br.com.wms.link_shortener.data.model.CreateAliasDTO
import br.com.wms.link_shortener.data.model.CreateAliasRequest
import br.com.wms.link_shortener.domain.repository.LinkShortenerRepository

internal class LinkShortenerRepositoryImpl(
    private val serviceApi: LinkShortenerServiceApi,
) : LinkShortenerRepository {
    override suspend fun createAlias(createAliasRequest: CreateAliasRequest): CreateAliasDTO = serviceApi.createAlias(createAliasRequest)
}
