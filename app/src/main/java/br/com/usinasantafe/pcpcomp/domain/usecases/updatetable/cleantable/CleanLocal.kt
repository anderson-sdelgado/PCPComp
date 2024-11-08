package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository

interface CleanLocal {
    suspend operator fun invoke(): Result<Boolean>
}

class CleanLocalImpl(
    private val localRepository: LocalRepository
): CleanLocal {

    override suspend fun invoke(): Result<Boolean> {
        return localRepository.deleteAll()
    }

}