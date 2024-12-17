package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetNroEquipChave {
    suspend operator fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetNroEquipChave(
): SetNroEquipChave {

    override suspend fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}