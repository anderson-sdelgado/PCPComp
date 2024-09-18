package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

interface GetVeiculoResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class GetVeiculoResidenciaImpl() : GetVeiculoResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        TODO("Not yet implemented")
    }

}