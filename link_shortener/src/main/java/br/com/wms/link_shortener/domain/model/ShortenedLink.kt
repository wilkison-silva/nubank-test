package br.com.wms.link_shortener.domain.model

internal data class ShortenedLink(
    val alias: String,
    val links: Links,
) {
    data class Links(
        val originalUrl: String,
        val short: String,
    )
}
