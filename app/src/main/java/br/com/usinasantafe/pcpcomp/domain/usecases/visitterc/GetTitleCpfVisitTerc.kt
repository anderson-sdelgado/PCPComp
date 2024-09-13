package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface GetTitleCpfVisitTerc {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<String>
}

class GetTitleCpfVisitTercImpl() : GetTitleCpfVisitTerc {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<String> {
        TODO("Not yet implemented")
    }

}