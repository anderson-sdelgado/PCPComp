package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface GetCpfVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetCpfVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
): GetCpfVisitTerc {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultGetType = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = id
            )
            if (resultGetType.isFailure)
                return Result.failure(resultGetType.exceptionOrNull()!!)
            val typeVisitTerc = resultGetType.getOrNull()!!
            val resultGetIdVisitTerc = movEquipVisitTercRepository.getIdVisitTerc(id)
            if (resultGetIdVisitTerc.isFailure)
                return Result.failure(resultGetIdVisitTerc.exceptionOrNull()!!)
            val idVisitTerc = resultGetIdVisitTerc.getOrNull()!!
            val resultGetCpf = when(typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getCpf(idVisitTerc)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getCpf(idVisitTerc)
            }
            if (resultGetCpf.isFailure)
                return Result.failure(resultGetCpf.exceptionOrNull()!!)
            return Result.success(resultGetCpf.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetCpfVisitTercImpl",
                    cause = e
                )
            )
        }
    }

}