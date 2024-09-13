package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface GetPlacaVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class GetPlacaVisitTercImpl() : GetPlacaVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        TODO("Not yet implemented")
    }

}