package br.com.wms.linkshortener.data.model

import com.google.gson.annotations.SerializedName

internal data class CreateAliasRequest(
    @SerializedName("url")
    val url: String,
)
