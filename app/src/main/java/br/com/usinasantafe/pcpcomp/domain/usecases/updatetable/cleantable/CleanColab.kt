package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository

interface CleanColab {
    suspend operator fun invoke(): Result<Boolean>
}

class CleanColabImpl(
    private val colabRepository: ColabRepository
): CleanColab {

    override suspend fun invoke(): Result<Boolean> {
        return colabRepository.deleteAll()
    }

}