package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface GetPlacaResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetPlacaResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetPlacaResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipResidenciaRepository.getPlaca(id = id)
    }

}