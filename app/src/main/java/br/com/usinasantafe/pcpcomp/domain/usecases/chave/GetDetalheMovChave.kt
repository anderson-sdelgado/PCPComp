package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.presenter.chave.detalhe.DetalheChaveModel
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheMovChave {
    suspend operator fun invoke(id: Int): Result<DetalheChaveModel>
}

class IGetDetalheMovChave(
    private val movChaveRepository: MovChaveRepository,
    private val colabRepository: ColabRepository,
    private val getDescrFullChave: GetDescrFullChave
    ): GetDetalheMovChave {

    override suspend fun invoke(id: Int): Result<DetalheChaveModel> {
        try {
            val resultMovChave = movChaveRepository.get(id)
            if (resultMovChave.isFailure)
                return Result.failure(resultMovChave.exceptionOrNull()!!)
            val entity = resultMovChave.getOrNull()!!
            val resultNomeColab = colabRepository.getNome(entity.matricColabMovChave!!)
            if (resultNomeColab.isFailure)
                return Result.failure(resultNomeColab.exceptionOrNull()!!)
            val nomeColab = resultNomeColab.getOrNull()!!
            val resultGetDescrFullChave = getDescrFullChave(entity.idChaveMovChave!!)
            if (resultGetDescrFullChave.isFailure)
                return Result.failure(resultGetDescrFullChave.exceptionOrNull()!!)
            val descrFullChave = resultGetDescrFullChave.getOrNull()!!
            return Result.success(
                DetalheChaveModel(
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(entity.dthrMovChave),
                    tipoMov = when (entity.tipoMovChave!!) {
                        TypeMovKey.REMOVE -> "RETIRADA"
                        TypeMovKey.RECEIPT -> "DEVOLUÇÃO"
                    },
                    chave = descrFullChave,
                    colab = "${entity.matricColabMovChave} - $nomeColab",
                    observ = entity.observMovChave
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IGetDetalheMovChave",
                    cause = e
                )
            )
        }
    }

}