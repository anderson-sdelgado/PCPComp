package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.presenter.visitterc.nome.NomeVisitTercModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante

interface GetNomeVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<NomeVisitTercModel>
}

class GetNomeVisitTercImpl() : GetNomeVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<NomeVisitTercModel> {
        TODO("Not yet implemented")
    }

}