package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface GetMotorista {
    suspend operator fun invoke(
        typeVisitTerc: TypeVisitTerc,
        idVisitTerc: Int
    ): Result<String>
}

class GetMotoristaImpl(
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
): GetMotorista {

    override suspend fun invoke(
        typeVisitTerc: TypeVisitTerc,
        idVisitTerc: Int
    ): Result<String> {
        when (typeVisitTerc) {
            TypeVisitTerc.VISITANTE -> {
                val resultGetVisit =
                    visitanteRepository.get(idVisitTerc)
                if (resultGetVisit.isFailure)
                    return Result.failure(resultGetVisit.exceptionOrNull()!!)
                val visitante = resultGetVisit.getOrNull()!!
                return Result.success("${visitante.cpfVisitante} - ${visitante.nomeVisitante}")
            }

            TypeVisitTerc.TERCEIRO -> {
                val resultGetTerc =
                    terceiroRepository.get(idVisitTerc)
                if (resultGetTerc.isFailure)
                    return Result.failure(resultGetTerc.exceptionOrNull()!!)
                val terceiro = resultGetTerc.getOrNull()!!
                return Result.success("${terceiro.cpfTerceiro} - ${terceiro.nomeTerceiro}")
            }
        }
    }

}