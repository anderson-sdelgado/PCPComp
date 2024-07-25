package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository

interface SaveAllColab {
    suspend operator fun invoke(list: List<Colab>): Result<Boolean>
}

class SaveAllColabImpl(
    private val colabRepository: ColabRepository,
): SaveAllColab {

    override suspend fun invoke(list: List<Colab>): Result<Boolean> {
        return colabRepository.addAll(list)
    }

}