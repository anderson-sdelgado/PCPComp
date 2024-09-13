package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface GetDestinoVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class GetDestinoVisitTercImpl() : GetDestinoVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        TODO("Not yet implemented")
    }

}