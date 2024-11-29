package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface GetTitleCpfVisitTerc {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<String>
}

class IGetTitleCpfVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetTitleCpfVisitTerc {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<String> {
        try {
            val result = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            val typeVisitTerc = result.getOrNull()!!.name
            return Result.success(typeVisitTerc)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "GetTitleCpfVisitTercImpl",
                    cause = e
                )
            )
        }
    }

}