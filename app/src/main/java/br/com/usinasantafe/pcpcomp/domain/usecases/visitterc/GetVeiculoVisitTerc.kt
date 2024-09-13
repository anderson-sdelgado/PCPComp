package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface GetVeiculoVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class GetVeiculoVisitTercImpl() : GetVeiculoVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        TODO("Not yet implemented")
    }

}