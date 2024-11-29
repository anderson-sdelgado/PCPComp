package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository

interface SaveColab {
    suspend operator fun invoke(list: List<Colab>): Result<Boolean>
}

class ISaveColab(
    private val colabRepository: ColabRepository,
): SaveColab {

    override suspend fun invoke(list: List<Colab>): Result<Boolean> {
        return colabRepository.addAll(list)
    }

}