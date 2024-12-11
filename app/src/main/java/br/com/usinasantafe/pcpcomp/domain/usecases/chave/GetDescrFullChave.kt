package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalTrabRepository

interface GetDescrFullChave {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetDescrFullChave(
    private val chaveRepository: ChaveRepository,
    private val localTrabRepository: LocalTrabRepository,
): GetDescrFullChave {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultChave = chaveRepository.get(id)
            if (resultChave.isFailure) {
                return Result.failure(
                    resultChave.exceptionOrNull()!!
                )
            }
            val entity = resultChave.getOrNull()!!
            val resultDescrLocalTrab = localTrabRepository.getDescr(
                entity.idLocalTrab
            )
            if (resultDescrLocalTrab.isFailure) {
                return Result.failure(
                    resultDescrLocalTrab.exceptionOrNull()!!
                )
            }
            val descrLocalTrab = resultDescrLocalTrab.getOrNull()!!
            return Result.success("${entity.descrChave} - $descrLocalTrab")
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IGetDescrFullChave",
                    cause = e
                )
            )
        }
    }

}