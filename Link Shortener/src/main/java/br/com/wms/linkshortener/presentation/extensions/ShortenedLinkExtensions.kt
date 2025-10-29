package br.com.wms.linkshortener.presentation.extensions

import br.com.wms.linkshortener.domain.model.ShortenedLink
import br.com.wms.linkshortener.presentation.model.ShortenedLinkView

internal fun ShortenedLink.toShortenedLinkView(): ShortenedLinkView {
    return ShortenedLinkView(
        alias = this.alias,
        originalUrl = this.links.originalUrl,
        short = this.links.short,
    )
}