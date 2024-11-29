package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository

interface CleanVisitante {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanVisitante(
    private val visitanteRepository: VisitanteRepository
): CleanVisitante {

    override suspend fun invoke(): Result<Boolean> {
        return visitanteRepository.deleteAll()
    }

}