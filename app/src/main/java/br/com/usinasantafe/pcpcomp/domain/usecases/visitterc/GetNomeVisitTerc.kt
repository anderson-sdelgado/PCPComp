package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.presenter.visitterc.nome.NomeVisitTercModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface GetNomeVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<NomeVisitTercModel>
}

class IGetNomeVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
) : GetNomeVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<NomeVisitTercModel> {
        try {
            val resultTypeVisitTerc = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (resultTypeVisitTerc.isFailure)
                return Result.failure(resultTypeVisitTerc.exceptionOrNull()!!)
            val typeVisitTerc = resultTypeVisitTerc.getOrNull()!!
            val resultNomeVisitTerc = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getNome(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getNome(cpf)
            }
            if (resultNomeVisitTerc.isFailure)
                return Result.failure(resultNomeVisitTerc.exceptionOrNull()!!)
            val resultEmpresaVisitTerc = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getEmpresas(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getEmpresas(cpf)
            }
            if (resultEmpresaVisitTerc.isFailure)
                return Result.failure(resultEmpresaVisitTerc.exceptionOrNull()!!)
            return Result.success(
                NomeVisitTercModel(
                    tipo = typeVisitTerc.name,
                    nome = resultNomeVisitTerc.getOrNull()!!,
                    empresa = resultEmpresaVisitTerc.getOrNull()!!
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetNomeVisitTercImpl",
                    cause = e
                )
            )
        }
    }

}