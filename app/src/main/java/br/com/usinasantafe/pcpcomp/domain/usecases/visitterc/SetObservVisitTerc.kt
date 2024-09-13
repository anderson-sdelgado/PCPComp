package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface SetObservVisitTerc {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean>
}

class SetObservVisitTercImpl() : SetObservVisitTerc {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}