package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository

interface SaveAllTerceiro {
    suspend operator fun invoke(list: List<Terceiro>): Result<Boolean>
}

class SaveAllTerceiroImpl(
    private val terceiroRepository: TerceiroRepository,
): SaveAllTerceiro {

    override suspend fun invoke(list: List<Terceiro>): Result<Boolean> {
        return terceiroRepository.addAll(list)
    }

}