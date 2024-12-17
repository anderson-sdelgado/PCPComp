package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.presenter.chave.model.ControleChaveModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovChaveInsideList {
    suspend operator fun invoke(): Result<List<ControleChaveModel>>
}

class IGetMovChaveInsideList(
    private val movChaveRepository: MovChaveRepository,
    private val colabRepository: ColabRepository,
    private val getDescrFullChave: GetDescrFullChave,
) : GetMovChaveInsideList {

    override suspend fun invoke(): Result<List<ControleChaveModel>> {
        try {
            val resultList = movChaveRepository.listInside()
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val entityList = resultList.getOrNull()!!.map {
                val resultNomeColab = colabRepository.getNome(it.matricColabMovChave!!)
                if (resultNomeColab.isFailure)
                    return Result.failure(resultNomeColab.exceptionOrNull()!!)
                val nomeColab = resultNomeColab.getOrNull()!!
                val resultGetDescrFullChave = getDescrFullChave(it.idChaveMovChave!!)
                if (resultGetDescrFullChave.isFailure)
                    return Result.failure(resultGetDescrFullChave.exceptionOrNull()!!)
                val descrFullChave = resultGetDescrFullChave.getOrNull()!!
                ControleChaveModel(
                    id = it.idMovChave!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovChave),
                    chave = descrFullChave,
                    colab = "${it.matricColabMovChave!!} - $nomeColab"
                )
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IGetMovChaveInsideList",
                    cause = e
                )
            )
        }
    }

}