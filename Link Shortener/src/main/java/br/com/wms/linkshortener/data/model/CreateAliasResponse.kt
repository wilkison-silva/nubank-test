package br.com.wms.linkshortener.data.model

import com.google.gson.annotations.SerializedName

internal data class CreateAliasResponse(
    @SerializedName("alias")
    val urlAlias: String,
    @SerializedName("_links")
    val links: Links
) {
    data class Links(
        @SerializedName("self")
        val originalUrl: String,
        @SerializedName("short")
        val short: String
    )
}