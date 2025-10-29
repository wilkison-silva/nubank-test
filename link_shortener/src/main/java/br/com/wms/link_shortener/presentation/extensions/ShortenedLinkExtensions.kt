package br.com.wms.link_shortener.presentation.extensions

import br.com.wms.link_shortener.domain.model.ShortenedLink
import br.com.wms.link_shortener.presentation.model.ShortenedLinkView

internal fun ShortenedLink.toShortenedLinkView(): ShortenedLinkView =
    ShortenedLinkView(
        alias = this.alias,
        originalUrl = this.links.originalUrl,
        short = this.links.short,
    )
