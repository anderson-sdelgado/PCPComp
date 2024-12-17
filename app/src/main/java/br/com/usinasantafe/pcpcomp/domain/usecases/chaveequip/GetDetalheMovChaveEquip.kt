package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.presenter.chaveequip.detalhe.DetalheChaveEquipModel

interface GetDetalheMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<DetalheChaveEquipModel>
}

class IGetDetalheMovChaveEquip(): GetDetalheMovChaveEquip {

    override suspend fun invoke(id: Int): Result<DetalheChaveEquipModel> {
        TODO("Not yet implemented")
    }

}