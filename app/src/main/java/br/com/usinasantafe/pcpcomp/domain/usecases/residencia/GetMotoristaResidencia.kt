package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

interface GetMotoristaResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class GetMotoristaResidenciaImpl() : GetMotoristaResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        TODO("Not yet implemented")
    }

}