package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.util.Date

interface StartReceiptMovChave {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartReceiptMovChave(
    private val movChaveRepository: MovChaveRepository
): StartReceiptMovChave {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultGet = movChaveRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.observMovChave = null
            movChave.tipoMovChave = TypeMovKey.RECEIPT
            movChave.dthrMovChave = Date()
            movChave.statusForeignerMovChave = StatusForeigner.OUTSIDE
            val resultStart = movChaveRepository.start(movChave)
            if (resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartReceiptChaveImpl",
                    cause = e.cause
                )
            )
        }
    }

}