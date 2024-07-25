package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository

interface SaveAllLocal {
    suspend operator fun invoke(list: List<Local>): Result<Boolean>
}

class SaveAllLocalImpl(
    private val localRepository: LocalRepository,
): SaveAllLocal {

    override suspend fun invoke(list: List<Local>): Result<Boolean> {
        return localRepository.addAll(list)
    }

}