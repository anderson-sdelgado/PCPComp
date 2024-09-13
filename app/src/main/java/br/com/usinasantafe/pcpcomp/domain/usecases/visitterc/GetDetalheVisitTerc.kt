package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe.DetalheVisitTercModel

interface GetDetalheVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheVisitTercModel>
}

class GetDetalheVisitTercImpl(): GetDetalheVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheVisitTercModel> {
        TODO("Not yet implemented")
    }

}