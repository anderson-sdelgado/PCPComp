package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository

interface SaveRLocalFluxo {
    suspend operator fun invoke(list: List<RLocalFluxo>): Result<Boolean>
}

class ISaveRLocalFluxo(
    private val rLocalFluxoRepository: RLocalFluxoRepository,
): SaveRLocalFluxo {

    override suspend fun invoke(list: List<RLocalFluxo>): Result<Boolean> {
        return rLocalFluxoRepository.addAll(list)
    }

}