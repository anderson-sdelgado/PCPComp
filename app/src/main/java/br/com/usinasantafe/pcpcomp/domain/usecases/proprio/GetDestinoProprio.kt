package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface GetDestinoProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class GetDestinoProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetDestinoProprio {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        try {
            val resultDestino = movEquipProprioRepository.getDestino(id = id)
            if (resultDestino.isFailure)
                return Result.failure(resultDestino.exceptionOrNull()!!)
            return Result.success(resultDestino.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetDestinoProprioImpl",
                    cause = e
                )
            )
        }
    }

}