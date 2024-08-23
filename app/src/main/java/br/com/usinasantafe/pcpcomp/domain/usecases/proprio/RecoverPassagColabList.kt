package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository

interface RecoverPassagColabList {
    suspend operator fun invoke(): Result<List<Colab>>
}

class RecoverPassagColabListImpl(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val colabRepository: ColabRepository
): RecoverPassagColabList {

    override suspend fun invoke(): Result<List<Colab>> {
        try {
            val resultList = movEquipProprioPassagRepository.list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val passagList = resultList.getOrNull()!!
            val passagColabList = passagList.map {
                val resultNomeColab = colabRepository.getNome(it)
                if(resultNomeColab.isFailure)
                    return Result.failure(resultNomeColab.exceptionOrNull()!!)
                Colab(
                    matricColab = it,
                    nomeColab = resultNomeColab.getOrNull()!!
                )
            }
            return Result.success(passagColabList)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "RecoverPassagColab",
                    cause = e
                )
            )
        }
    }

}