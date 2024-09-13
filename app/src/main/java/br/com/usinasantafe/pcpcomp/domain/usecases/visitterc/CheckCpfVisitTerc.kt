package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante

interface CheckCpfVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class CheckCpfVisitTercImpl() : CheckCpfVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}