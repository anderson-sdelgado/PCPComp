package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository

interface GetObservMovChave {
    suspend operator fun invoke(id: Int): Result<String?>
}

class IGetObservMovChave(
    private val movChaveRepository: MovChaveRepository
): GetObservMovChave {

    override suspend fun invoke(id: Int): Result<String?> {
        return movChaveRepository.getObserv(id)
    }

}