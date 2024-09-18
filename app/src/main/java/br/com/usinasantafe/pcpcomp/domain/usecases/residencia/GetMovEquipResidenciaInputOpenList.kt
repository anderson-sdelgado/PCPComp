package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.presenter.residencia.model.MovEquipResidenciaModel

interface GetMovEquipResidenciaInputOpenList {
    suspend operator fun invoke(): Result<List<MovEquipResidenciaModel>>
}

class GetMovEquipResidenciaInputOpenListImpl(): GetMovEquipResidenciaInputOpenList {

    override suspend fun invoke(): Result<List<MovEquipResidenciaModel>> {
        TODO("Not yet implemented")
    }

}