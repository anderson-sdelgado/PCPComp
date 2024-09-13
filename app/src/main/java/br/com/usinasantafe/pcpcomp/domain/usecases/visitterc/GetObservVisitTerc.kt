package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface GetObservVisitTerc {
    suspend operator fun invoke(
        id: Int,
    ): Result<String?>
}

class GetObservVisitTercImpl() : GetObservVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        TODO("Not yet implemented")
    }

}