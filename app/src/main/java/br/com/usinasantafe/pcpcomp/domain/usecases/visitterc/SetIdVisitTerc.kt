package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface SetIdVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean>
}

class SetIdVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository,
    private val startProcessSendData: StartProcessSendData
) : SetIdVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean> {
        try {
            val resultGetType = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (resultGetType.isFailure)
                return Result.failure(resultGetType.exceptionOrNull()!!)
            val typeVisitTerc = resultGetType.getOrNull()!!
            val resultId = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getId(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getId(cpf)
            }
            if (resultId.isFailure)
                return Result.failure(resultId.exceptionOrNull()!!)
            val idVisitTerc = resultId.getOrNull()!!
            when (typeOcupante) {
                TypeOcupante.MOTORISTA -> {
                    val resultSet = movEquipVisitTercRepository.setIdVisitTerc(
                        idVisitTerc = idVisitTerc,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultSet.isFailure)
                        return Result.failure(resultSet.exceptionOrNull()!!)
                }
                TypeOcupante.PASSAGEIRO -> {
                    val resultAdd = movEquipVisitTercPassagRepository.add(
                        idVisitTerc = idVisitTerc,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultAdd.isFailure)
                        return Result.failure(resultAdd.exceptionOrNull()!!)
                    if(flowApp == FlowApp.CHANGE){
                        val resultSend = movEquipVisitTercRepository.setSend(id)
                        if (resultSend.isFailure)
                            return Result.failure(resultSend.exceptionOrNull()!!)
                    }
                }
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SetIdVisitTerc",
                    cause = e
                )
            )
        }
    }

}