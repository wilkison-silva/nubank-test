package br.com.wms.link_shortener.presentation.model

internal data class ShortenedLinkView(
    val alias: String,
    val originalUrl: String,
    val short: String,
)
