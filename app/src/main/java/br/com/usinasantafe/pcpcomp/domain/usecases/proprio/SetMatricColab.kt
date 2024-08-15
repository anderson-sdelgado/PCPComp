package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante

interface SetMatricColab {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp = FlowApp.ADD,
        typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
        pos: Int = 0
    ): Result<Boolean>
}

class SetMatricColabImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
): SetMatricColab {

    override suspend fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        pos: Int
    ): Result<Boolean> {
        return try {
            when(flowApp){
                FlowApp.ADD -> {
                    when(typeOcupante){
                        TypeOcupante.MOTORISTA -> movEquipProprioRepository.setMatricColab(matricColab.toLong())
                        TypeOcupante.PASSAGEIRO -> TODO()
                    }
                }
                FlowApp.CHANGE -> TODO()
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