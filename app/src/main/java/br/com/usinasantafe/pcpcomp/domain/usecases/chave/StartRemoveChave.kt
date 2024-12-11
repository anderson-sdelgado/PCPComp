package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository

interface StartRemoveChave {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartRemoveChave(
    private val movChaveRepository: MovChaveRepository
): StartRemoveChave {

    override suspend fun invoke(): Result<Boolean> {
        return movChaveRepository.start()
    }

}