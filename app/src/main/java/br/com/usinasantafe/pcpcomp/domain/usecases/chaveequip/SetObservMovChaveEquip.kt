package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetObservMovChaveEquip {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetObservMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): SetObservMovChaveEquip {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            movChaveEquipRepository.setObserv(
                observ = observ,
                flowApp = flowApp,
                id = id
            )
        } catch (e: Exception) {
            Result.failure(
                UsecaseException(
                    function = "ISetObservMovChaveEquip",
                    cause = e
                )
            )
        }
    }

}