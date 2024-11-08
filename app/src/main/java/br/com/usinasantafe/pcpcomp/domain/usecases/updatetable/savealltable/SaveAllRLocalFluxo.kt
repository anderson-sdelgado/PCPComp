package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository

interface SaveAllRLocalFluxo {
    suspend operator fun invoke(list: List<RLocalFluxo>): Result<Boolean>
}

class SaveAllRLocalFluxoImpl(
    private val rLocalFluxoRepository: RLocalFluxoRepository,
): SaveAllRLocalFluxo {

    override suspend fun invoke(list: List<RLocalFluxo>): Result<Boolean> {
        return rLocalFluxoRepository.addAll(list)
    }

}