package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetDestinoProprio {
    suspend operator fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetDestinoProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
): SetDestinoProprio {

    override suspend fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return movEquipProprioRepository.setDestino(
            destino = destino,
            flowApp = flowApp,
            id = id
        )
    }

}