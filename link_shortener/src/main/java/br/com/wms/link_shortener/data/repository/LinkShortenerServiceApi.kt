package br.com.wms.link_shortener.data.repository

import br.com.wms.link_shortener.data.model.CreateAliasDTO
import br.com.wms.link_shortener.data.model.CreateAliasRequest
import retrofit2.http.Body
import retrofit2.http.POST

internal interface LinkShortenerServiceApi {
    @POST("/api/alias")
    suspend fun createAlias(
        @Body request: CreateAliasRequest,
    ): CreateAliasDTO
}
