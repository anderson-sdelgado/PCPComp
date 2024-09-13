package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante

interface SetCpfVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean>
}

class SetCpfVisitTercImpl() : SetCpfVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}