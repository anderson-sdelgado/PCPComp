package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetMotoristaResidencia {
    suspend operator fun invoke(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetMotoristaResidenciaImpl() : SetMotoristaResidencia {

    override suspend fun invoke(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}