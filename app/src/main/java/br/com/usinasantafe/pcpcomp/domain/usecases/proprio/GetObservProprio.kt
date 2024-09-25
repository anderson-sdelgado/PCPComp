package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface GetObservProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<String?>
}

class GetObservProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetObservProprio {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        return movEquipProprioRepository.getObserv(id = id)
    }

}