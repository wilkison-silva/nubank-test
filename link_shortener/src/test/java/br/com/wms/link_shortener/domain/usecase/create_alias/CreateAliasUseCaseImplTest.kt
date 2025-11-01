package br.com.wms.link_shortener.domain.usecase.create_alias

import br.com.wms.link_shortener.data.model.CreateAliasDTO
import br.com.wms.link_shortener.domain.model.ShortenedLink
import br.com.wms.link_shortener.domain.repository.LinkShortenerRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

internal class CreateAliasUseCaseImplTest {
    private val repository: LinkShortenerRepository = mockk()
    private val sut by lazy {
        CreateAliasUseCaseImpl(repository)
    }

    @Test
    fun `Successful alias creation with valid URL`() =
        runTest {
            // GIVEN
            val shortenedLink =
                ShortenedLink(
                    alias = "alias",
                    links =
                        ShortenedLink.Links(
                            originalUrl = "original",
                            short = "short",
                        ),
                )
            val dto =
                CreateAliasDTO(
                    urlAlias = "alias",
                    links =
                        CreateAliasDTO.Links(
                            originalUrl = "original",
                            short = "short",
                        ),
                )
            coEvery {
                repository.createAlias(any())
            } returns dto

            // WHEN
            val result = sut.invoke("www.google.com")

            // THEN
            Assert.assertEquals(result, CreateAliasUseCase.Result.Success(shortenedLink))
        }

    @Test
    fun `error alias creation with valid URL`() =
        runTest {
            // GIVEN
            coEvery {
                repository.createAlias(any())
            } throws Exception()

            // WHEN
            val result = sut.invoke("www.google.com")

            // THEN
            Assert.assertEquals(result, CreateAliasUseCase.Result.Error)
        }
}
