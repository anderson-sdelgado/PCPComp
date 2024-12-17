package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetMatricColabMovChaveEquip {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetMatricColabMovChaveEquip(): SetMatricColabMovChaveEquip {

    override suspend fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}