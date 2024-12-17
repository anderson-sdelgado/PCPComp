package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetObservMovChave {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetObservMovChave(
    private val movChaveRepository: MovChaveRepository,
    private val startProcessSendData: StartProcessSendData
): SetObservMovChave {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = movChaveRepository.setObserv(
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
                    function = "ISetObservMovChave",
                    cause = e
                )
            )
        }
    }

}