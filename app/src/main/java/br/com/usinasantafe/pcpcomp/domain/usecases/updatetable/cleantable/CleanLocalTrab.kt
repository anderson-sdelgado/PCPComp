package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalTrabRepository

interface CleanLocalTrab {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanLocalTrab(
    private val localTrabRepository: LocalTrabRepository
): CleanLocalTrab {

    override suspend fun invoke(): Result<Boolean> {
        return localTrabRepository.deleteAll()
    }

}