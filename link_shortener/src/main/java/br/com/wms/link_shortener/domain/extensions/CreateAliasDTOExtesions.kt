package br.com.wms.link_shortener.domain.extensions

import br.com.wms.link_shortener.data.model.CreateAliasDTO
import br.com.wms.link_shortener.domain.model.ShortenedLink

internal fun CreateAliasDTO.toShortenedLink(): ShortenedLink =
    ShortenedLink(
        alias = this.urlAlias,
        links =
            ShortenedLink.Links(
                originalUrl = this.links.originalUrl,
                short = this.links.short,
            ),
    )
