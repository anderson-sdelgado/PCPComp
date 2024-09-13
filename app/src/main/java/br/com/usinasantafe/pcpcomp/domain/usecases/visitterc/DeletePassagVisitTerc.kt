package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface DeletePassagVisitTerc {
    suspend operator fun invoke(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class DeletePassagVisitTercImpl() : DeletePassagVisitTerc {

    override suspend fun invoke(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}