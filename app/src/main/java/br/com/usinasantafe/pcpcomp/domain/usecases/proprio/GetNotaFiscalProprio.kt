package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface GetNotaFiscalProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<String?>
}

class GetNotaFiscalProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetNotaFiscalProprio {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        try {
            val resultNotaFiscal = movEquipProprioRepository.getNotaFiscal(id = id)
            if (resultNotaFiscal.isFailure)
                return Result.failure(resultNotaFiscal.exceptionOrNull()!!)
            val notaFiscal = if(resultNotaFiscal.getOrNull() == null) null else resultNotaFiscal.getOrNull().toString()
            return Result.success(notaFiscal)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetNotaFiscalImpl",
                    cause = e
                )
            )
        }
    }

}