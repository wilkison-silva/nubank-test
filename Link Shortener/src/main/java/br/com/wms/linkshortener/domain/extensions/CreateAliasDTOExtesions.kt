package br.com.wms.linkshortener.domain.extensions

import br.com.wms.linkshortener.data.model.CreateAliasDTO
import br.com.wms.linkshortener.domain.model.ShortenedLink

internal fun CreateAliasDTO.toShortenedLink(): ShortenedLink {
    return ShortenedLink(
        alias = this.urlAlias,
        links = ShortenedLink.Links(
            originalUrl = this.links.originalUrl,
            short = this.links.short,
        ),
    )
}