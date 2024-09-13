package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface CleanPassagVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class CleanPassagVisitTercImpl(): CleanPassagVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}