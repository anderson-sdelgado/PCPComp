package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetNotaFiscalProprio {
    suspend operator fun invoke(
        notaFiscal: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetNotaFiscalProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : SetNotaFiscalProprio {

    override suspend fun invoke(
        notaFiscal: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            movEquipProprioRepository.setNotaFiscal(notaFiscal = notaFiscal.toInt(), flowApp = flowApp, id = id)
        } catch (e: Exception) {
            Result.failure(
                UsecaseException(
                    function = "SetNotaFiscalProprio",
                    cause = e
                )
            )
        }
    }

}