package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface GetPassagColabList {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Colab>>
}

class IGetPassagColabList(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val colabRepository: ColabRepository
) : GetPassagColabList {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Colab>> {
        try {
            val resultList = movEquipProprioPassagRepository.list(flowApp, id)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val passagList = resultList.getOrNull()!!
            val passagColabList = passagList.map {
                val resultNomeColab = colabRepository.getNome(it.matricColab!!)
                if (resultNomeColab.isFailure)
                    return Result.failure(resultNomeColab.exceptionOrNull()!!)
                Colab(
                    matricColab = it.matricColab!!,
                    nomeColab = resultNomeColab.getOrNull()!!
                )
            }
            return Result.success(passagColabList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverPassagColab",
                    cause = e
                )
            )
        }
    }

}