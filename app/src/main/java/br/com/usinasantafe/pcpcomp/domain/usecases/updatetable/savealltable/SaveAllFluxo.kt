package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository

interface SaveAllFluxo {
    suspend operator fun invoke(list: List<Fluxo>): Result<Boolean>
}

class SaveAllFluxoImpl(
    private val fluxoRepository: FluxoRepository
): SaveAllFluxo {

    override suspend fun invoke(list: List<Fluxo>): Result<Boolean> {
        return fluxoRepository.addAll(list)
    }

}