package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository

interface RecoverLocals {
    suspend operator fun invoke(): Result<List<Local>>
}

class RecoverLocalsImpl(
    private val localRepository: LocalRepository
): RecoverLocals {

    override suspend fun invoke(): Result<List<Local>> {
        return localRepository.getAll()
    }

}