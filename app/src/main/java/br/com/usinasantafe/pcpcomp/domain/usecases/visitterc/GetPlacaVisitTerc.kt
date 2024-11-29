package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetPlacaVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetPlacaVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetPlacaVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipVisitTercRepository.getPlaca(id = id)
    }

}