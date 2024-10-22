package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date

interface StartOutputMovEquipVisitTerc {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class StartOutputMovEquipVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
) : StartOutputMovEquipVisitTerc {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultMov = movEquipVisitTercRepository.get(id)
            if (resultMov.isFailure)
                return Result.failure(resultMov.exceptionOrNull()!!)
            val movEquipVisitTerc = resultMov.getOrNull()!!
            movEquipVisitTerc.observMovEquipVisitTerc = null
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMov.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            val resultStart = movEquipVisitTercRepository.start(movEquipVisitTerc)
            if (resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            val resultPassagList = movEquipVisitTercPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = id
            )
            if (resultPassagList.isFailure)
                return Result.failure(resultPassagList.exceptionOrNull()!!)
            val passagList = resultPassagList.getOrNull()
            if (passagList != null) {
                for (passag in passagList) {
                    val resultAdd = movEquipVisitTercPassagRepository.add(
                        idVisitTerc = passag.idVisitTerc!!,
                        flowApp = FlowApp.ADD,
                        id = 0
                    )
                    if (resultAdd.isFailure)
                        return Result.failure(resultAdd.exceptionOrNull()!!)
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartMovEquipVisitTercImpl",
                    cause = e.cause
                )
            )
        }
    }

}