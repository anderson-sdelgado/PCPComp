package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface GetCpfVisitTerc {
    suspend operator fun invoke(id: Int): Result<String>
}

class GetCpfVisitTercImpl(): GetCpfVisitTerc {

    override suspend fun invoke(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

}