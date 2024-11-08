package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository

interface SaveAllVisitante {
    suspend operator fun invoke(list: List<Visitante>): Result<Boolean>
}

class SaveAllVisitanteImpl(
    private val colabRepository: VisitanteRepository,
): SaveAllVisitante {

    override suspend fun invoke(list: List<Visitante>): Result<Boolean> {
        return colabRepository.addAll(list)
    }

}