package br.com.wms.linkshortener.data.model

import com.google.gson.annotations.SerializedName

internal data class GetUrlResponse(
    @SerializedName("url")
    val url: String,
)