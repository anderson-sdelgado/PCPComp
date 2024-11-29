package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface CheckCpfVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ICheckCpfVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
) : CheckCpfVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultTypeVisitTerc = movEquipVisitTercRepository.getTypeVisitTerc(flowApp, id)
            if (resultTypeVisitTerc.isFailure)
                return Result.failure(resultTypeVisitTerc.exceptionOrNull()!!)
            val typeVisitTerc = resultTypeVisitTerc.getOrNull()!!
            val resultCheck = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.checkCPF(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.checkCPF(cpf)
            }
            if (resultCheck.isFailure)
                return Result.failure(resultCheck.exceptionOrNull()!!)
            val result = resultCheck.getOrNull()!!
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckCpfVisitTerc",
                    cause = e
                )
            )
        }
    }

}