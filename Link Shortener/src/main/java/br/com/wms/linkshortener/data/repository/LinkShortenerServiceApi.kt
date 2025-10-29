package br.com.wms.linkshortener.data.repository

import br.com.wms.linkshortener.data.model.CreateAliasRequest
import br.com.wms.linkshortener.data.model.CreateAliasResponse
import br.com.wms.linkshortener.data.model.GetUrlResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface LinkShortenerServiceApi {

    @POST("/api/alias")
    suspend fun createAlias(
        @Body request: CreateAliasRequest
    ): CreateAliasResponse

    @GET("/api/alias/{aliasId}")
    suspend fun getUrlFromAlias(
        @Path("aliasid") aliasId: String,
    ): GetUrlResponse
}