package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetObservProprio {
    suspend operator fun invoke(
        observ: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetObservProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : SetObservProprio {

    override suspend fun invoke(
        observ: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return movEquipProprioRepository.setObserv(
            observ = observ,
            flowApp = flowApp,
            id = id
        )
    }

}