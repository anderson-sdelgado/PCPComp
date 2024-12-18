package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetObservMovChaveEquip {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetObservMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val startProcessSendData: StartProcessSendData
): SetObservMovChaveEquip {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = movChaveEquipRepository.setObserv(
                observ = observ,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "ISetObservMovChaveEquip",
                    cause = e
                )
            )
        }
    }

}