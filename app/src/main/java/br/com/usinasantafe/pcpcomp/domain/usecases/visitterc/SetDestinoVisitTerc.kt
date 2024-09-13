package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetDestinoVisitTerc {
    suspend operator fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetDestinoVisitTercImpl() : SetDestinoVisitTerc {

    override suspend fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}