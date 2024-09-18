package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

interface GetObservResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String?>
}

class GetObservResidenciaImpl() : GetObservResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        TODO("Not yet implemented")
    }

}