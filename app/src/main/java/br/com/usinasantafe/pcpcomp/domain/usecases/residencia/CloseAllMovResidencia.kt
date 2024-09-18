package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

interface CloseAllMovResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class CloseAllMovResidenciaImpl(): CloseAllMovResidencia {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}