package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetObservMovChaveEquip {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetObservMovChaveEquip(): SetObservMovChaveEquip {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}