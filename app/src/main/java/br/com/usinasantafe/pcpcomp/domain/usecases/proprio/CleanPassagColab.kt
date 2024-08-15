package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository

interface CleanPassagColab {
    suspend operator fun invoke(): Result<Boolean>
}

class CleanPassagColabImpl(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
) : CleanPassagColab {

    override suspend fun invoke(): Result<Boolean> {
        return movEquipProprioPassagRepository.clear()
    }

}