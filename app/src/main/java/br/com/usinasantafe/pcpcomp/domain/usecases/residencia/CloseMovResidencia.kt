package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

interface CloseMovResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class CloseMovResidenciaImpl() : CloseMovResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}