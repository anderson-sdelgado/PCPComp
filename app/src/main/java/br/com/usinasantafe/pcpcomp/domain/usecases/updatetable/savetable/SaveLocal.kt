package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository

interface SaveLocal {
    suspend operator fun invoke(list: List<Local>): Result<Boolean>
}

class ISaveLocal(
    private val localRepository: LocalRepository,
): SaveLocal {

    override suspend fun invoke(list: List<Local>): Result<Boolean> {
        return localRepository.addAll(list)
    }

}