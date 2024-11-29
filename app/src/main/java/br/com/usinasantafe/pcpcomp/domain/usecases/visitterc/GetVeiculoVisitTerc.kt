package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetVeiculoVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetVeiculoVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetVeiculoVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipVisitTercRepository.getVeiculo(id = id)
    }

}