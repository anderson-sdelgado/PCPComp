package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface DeletePassagColab {
    suspend operator fun invoke(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class DeletePassagColabImpl(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
): DeletePassagColab {

    override suspend fun invoke(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return movEquipProprioPassagRepository.delete(
            matricColab = matricColab,
            flowApp = flowApp,
            id = id
        )
    }

}