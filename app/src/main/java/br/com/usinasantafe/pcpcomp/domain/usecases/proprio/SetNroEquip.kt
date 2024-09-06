package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip

interface SetNroEquip {
    suspend operator fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        typeEquip: TypeEquip,
        id: Int
    ): Result<Boolean>
}

class SetNroEquipImpl(
    private val equipRepository: EquipRepository,
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val startProcessSendData: StartProcessSendData
) : SetNroEquip {

    override suspend fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        typeEquip: TypeEquip,
        id: Int
    ): Result<Boolean> {
        try {
            val resultId = equipRepository.getId(nroEquip.toLong())
            if (resultId.isFailure)
                return Result.failure(resultId.exceptionOrNull()!!)
            val idEquip = resultId.getOrNull()!!
            when (typeEquip) {
                TypeEquip.VEICULO -> {
                    val resultSet = movEquipProprioRepository.setIdEquip(
                        idEquip = idEquip,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultSet.isFailure)
                        return Result.failure(resultSet.exceptionOrNull()!!)
                }

                TypeEquip.VEICULOSEG -> {
                    val resultAdd = movEquipProprioEquipSegRepository.add(
                        idEquip = idEquip,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultAdd.isFailure)
                        return Result.failure(resultAdd.exceptionOrNull()!!)
                    if(flowApp == FlowApp.CHANGE) {
                        val resultSend = movEquipProprioRepository.setSend(id)
                        if (resultSend.isFailure)
                            return Result.failure(resultSend.exceptionOrNull()!!)
                    }
                }
            }
            if(flowApp == FlowApp.CHANGE){
                startProcessSendData()
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SetNroEquipProprio",
                    cause = e
                )
            )
        }
    }

}