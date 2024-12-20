package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetDestinoVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetDestinoVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetDestinoVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipVisitTercRepository.getDestino(id = id)
    }

}