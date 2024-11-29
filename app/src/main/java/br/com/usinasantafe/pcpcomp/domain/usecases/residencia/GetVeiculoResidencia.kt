package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface GetVeiculoResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetVeiculoResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetVeiculoResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipResidenciaRepository.getVeiculo(id = id)
    }

}