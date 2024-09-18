package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface SetObservResidencia {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean>
}

class SetObservResidenciaImpl() : SetObservResidencia {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}