package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface DeletePassagVisitTerc {
    suspend operator fun invoke(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class IDeletePassagVisitTerc(
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository
) : DeletePassagVisitTerc {

    override suspend fun invoke(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return movEquipVisitTercPassagRepository.delete(
            idVisitTerc = idVisitTerc,
            flowApp = flowApp,
            id = id
        )
    }

}