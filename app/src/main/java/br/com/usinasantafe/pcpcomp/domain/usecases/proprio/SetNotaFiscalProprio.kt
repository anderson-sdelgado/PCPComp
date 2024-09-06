package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetNotaFiscalProprio {
    suspend operator fun invoke(
        notaFiscal: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetNotaFiscalProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val startProcessSendData: StartProcessSendData
) : SetNotaFiscalProprio {

    override suspend fun invoke(
        notaFiscal: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = movEquipProprioRepository.setNotaFiscal(
                notaFiscal = notaFiscal.toInt(),
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            if(flowApp == FlowApp.CHANGE){
                startProcessSendData()
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SetNotaFiscalProprio",
                    cause = e
                )
            )
        }
    }

}