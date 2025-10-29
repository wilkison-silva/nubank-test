package br.com.wms.linkshortener.domain.usecase.create_alias

internal interface CreateAliasUseCase {
    suspend operator fun invoke(url: String): Result

    sealed class Result {
        data class Success(val alias: String) : Result()
        data object Error : Result()
    }
}