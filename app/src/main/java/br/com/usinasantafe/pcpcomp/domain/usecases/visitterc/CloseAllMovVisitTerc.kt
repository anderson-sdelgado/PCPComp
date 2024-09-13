package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface CloseAllMovVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class CloseAllMovVisitTercImpl(): CloseAllMovVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}