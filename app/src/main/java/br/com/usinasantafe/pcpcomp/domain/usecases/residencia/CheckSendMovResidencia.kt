package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface CheckSendMovResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class CheckSendMovResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): CheckSendMovResidencia {

    override suspend fun invoke(): Result<Boolean> {
        return movEquipResidenciaRepository.checkSend()
    }

}