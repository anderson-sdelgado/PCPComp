package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante

interface SetMatricColab {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean>
}

class SetMatricColabImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
): SetMatricColab {

    override suspend fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean> {
        return try {
            when (typeOcupante) {
                TypeOcupante.MOTORISTA -> movEquipProprioRepository.setMatricColab(
                    matricColab = matricColab.toInt(),
                    flowApp = flowApp,
                    id = id
                )

                TypeOcupante.PASSAGEIRO -> movEquipProprioPassagRepository.add(
                    matricColab = matricColab.toInt(),
                    flowApp = flowApp,
                    id = id
                )
            }
        } catch (e: Exception){
            Result.failure(
                UsecaseException(
                    function = "SetMatricMotorista",
                    cause = e
                )
            )
        }
    }

}