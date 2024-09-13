package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetPlacaVisitTerc {
    suspend operator fun invoke(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetPlacaVisitTercImpl() : SetPlacaVisitTerc {

    override suspend fun invoke(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}