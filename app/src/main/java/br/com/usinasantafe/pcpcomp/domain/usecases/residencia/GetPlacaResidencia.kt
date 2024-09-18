package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

interface GetPlacaResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class GetPlacaResidenciaImpl() : GetPlacaResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        TODO("Not yet implemented")
    }

}