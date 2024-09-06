package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetDestinoProprio {
    suspend operator fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetDestinoProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val startProcessSendData: StartProcessSendData
): SetDestinoProprio {

    override suspend fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val resultSet = movEquipProprioRepository.setDestino(
            destino = destino,
            flowApp = flowApp,
            id = id
        )
        if (resultSet.isFailure)
            return Result.failure(resultSet.exceptionOrNull()!!)
        if(flowApp == FlowApp.CHANGE){
            startProcessSendData()
        }
        return Result.success(true)
    }

}