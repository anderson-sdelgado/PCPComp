package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface CloseMovVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class CloseMovVisitTercImpl(): CloseMovVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}