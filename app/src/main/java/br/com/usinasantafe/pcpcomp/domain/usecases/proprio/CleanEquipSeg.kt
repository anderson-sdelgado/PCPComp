package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository

interface CleanEquipSeg {
    suspend operator fun invoke(): Result<Boolean>
}

class CleanEquipSegImpl(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository
): CleanEquipSeg {

    override suspend fun invoke(): Result<Boolean> {
        return movEquipProprioEquipSegRepository.clear()
    }

}