package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.presenter.chaveequip.model.ControleChaveEquipModel

interface GetMovChaveEquipOpenList {
    suspend operator fun invoke(): Result<List<ControleChaveEquipModel>>
}

class IGetMovChaveEquipOpenList(): GetMovChaveEquipOpenList {

    override suspend fun invoke(): Result<List<ControleChaveEquipModel>> {
        TODO("Not yet implemented")
    }

}