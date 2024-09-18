package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe.DetalheResidenciaModel

interface GetDetalheResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheResidenciaModel>
}

class GetDetalheResidenciaImpl() : GetDetalheResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheResidenciaModel> {
        TODO("Not yet implemented")
    }

}