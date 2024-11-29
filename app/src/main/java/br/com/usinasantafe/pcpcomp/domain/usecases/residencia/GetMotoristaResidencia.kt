package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface GetMotoristaResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetMotoristaResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetMotoristaResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipResidenciaRepository.getMotorista(id = id)
    }

}