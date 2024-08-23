package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface DeleteEquipSeg {
    suspend operator fun invoke(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int,
    ): Result<Boolean>
}

class DeleteEquipSegImpl(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository
) : DeleteEquipSeg {

    override suspend fun invoke(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int,
    ): Result<Boolean> {
        return movEquipProprioEquipSegRepository.delete(
            idEquip = idEquip,
            flowApp = flowApp,
            id = id
        )
    }

}