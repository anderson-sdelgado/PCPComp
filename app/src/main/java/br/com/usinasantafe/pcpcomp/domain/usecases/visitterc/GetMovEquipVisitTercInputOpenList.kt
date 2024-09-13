package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.presenter.visitterc.model.MovEquipVisitTercModel

interface GetMovEquipVisitTercInputOpenList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTercModel>>
}

class GetMovEquipVisitTercInputOpenListImpl(): GetMovEquipVisitTercInputOpenList {

    override suspend fun invoke(): Result<List<MovEquipVisitTercModel>> {
        TODO("Not yet implemented")
    }

}