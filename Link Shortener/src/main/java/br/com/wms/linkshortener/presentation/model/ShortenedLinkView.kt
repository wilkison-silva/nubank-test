package br.com.wms.linkshortener.presentation.model

internal data class ShortenedLinkView(
    val alias: String,
    val originalUrl: String,
    val short: String,
)
