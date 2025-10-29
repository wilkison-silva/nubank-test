package br.com.wms.linkshortener.data.repository

import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.data.model.CreateAliasResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface LinkShortenerServiceApi {

    @POST("/api/alias")
    suspend fun createAlias(
        @Body request: CreateAliasRequest,
    ): CreateAliasResponse
}