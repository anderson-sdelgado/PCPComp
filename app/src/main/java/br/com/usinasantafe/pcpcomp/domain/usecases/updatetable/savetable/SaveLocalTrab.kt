package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalTrabRepository

interface SaveLocalTrab {
    suspend operator fun invoke(list: List<LocalTrab>): Result<Boolean>
}

class ISaveLocalTrab(
    private val localTrabRepository: LocalTrabRepository
): SaveLocalTrab {

    override suspend fun invoke(list: List<LocalTrab>): Result<Boolean> {
        return localTrabRepository.addAll(list)
    }

}