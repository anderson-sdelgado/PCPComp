package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository

interface CleanFluxo {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanFluxo(
    private val fluxoRepository: FluxoRepository
): CleanFluxo {

    override suspend fun invoke(): Result<Boolean> {
        return fluxoRepository.deleteAll()
    }

}