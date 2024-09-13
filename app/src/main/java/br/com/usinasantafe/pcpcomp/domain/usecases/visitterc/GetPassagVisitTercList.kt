package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist.PassagVisitTercModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface GetPassagVisitTercList {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<PassagVisitTercModel>>
}

class GetPassagVisitTercListImpl() : GetPassagVisitTercList {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<PassagVisitTercModel>> {
        TODO("Not yet implemented")
    }

}