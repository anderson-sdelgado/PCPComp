package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository

interface StartRemoveMovChave {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartRemoveMovChave(
    private val movChaveRepository: MovChaveRepository
): StartRemoveMovChave {

    override suspend fun invoke(): Result<Boolean> {
        return movChaveRepository.start()
    }

}