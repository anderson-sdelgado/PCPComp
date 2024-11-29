package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ChaveRepository

interface SaveChave {
    suspend operator fun invoke(list: List<Chave>): Result<Boolean>
}

class ISaveChave(
    private val chaveRepository: ChaveRepository
): SaveChave {

    override suspend fun invoke(list: List<Chave>): Result<Boolean> {
        return chaveRepository.addAll(list)
    }

}