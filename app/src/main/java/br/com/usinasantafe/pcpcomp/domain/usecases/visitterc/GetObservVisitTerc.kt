package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetObservVisitTerc {
    suspend operator fun invoke(
        id: Int,
    ): Result<String?>
}

class GetObservVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetObservVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        return movEquipVisitTercRepository.getObserv(id = id)
    }

}