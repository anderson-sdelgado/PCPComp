package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetVeiculoVisitTerc {
    suspend operator fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetVeiculoVisitTercImpl() : SetVeiculoVisitTerc {

    override suspend fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}