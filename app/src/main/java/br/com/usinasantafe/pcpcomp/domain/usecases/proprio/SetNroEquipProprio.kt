package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip

interface SetNroEquipProprio {
    suspend operator fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        typeEquip: TypeEquip,
        id: Int
    ): Result<Boolean>
}

class SetNroEquipProprioImpl(
    private val equipRepository: EquipRepository,
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository
) : SetNroEquipProprio {

    override suspend fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        typeEquip: TypeEquip,
        id: Int
    ): Result<Boolean> {
        return try {
            val resultId = equipRepository.getId(nroEquip.toLong())
            if (resultId.isFailure)
                return Result.failure(resultId.exceptionOrNull()!!)
            val idEquip = resultId.getOrNull()!!
            if (idEquip == 0) {
                return Result.failure(
                    UsecaseException(
                        function = "SetNroEquipProprio",
                        cause = NullPointerException()
                    )
                )
            }
            when (typeEquip) {
                TypeEquip.VEICULO -> movEquipProprioRepository.setIdEquip(
                    idEquip = idEquip,
                    flowApp = flowApp,
                    id = id
                )

                TypeEquip.VEICULOSEG -> movEquipProprioEquipSegRepository.add(
                    idEquip = idEquip,
                    flowApp = flowApp,
                    id = id
                )
            }
        } catch (e: Exception) {
            Result.failure(
                UsecaseException(
                    function = "SetNroEquipProprio",
                    cause = e
                )
            )
        }
    }

}