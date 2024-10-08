package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface CheckSendMovVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class CheckSendMovVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
): CheckSendMovVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        return movEquipVisitTercRepository.checkSend()
    }

}