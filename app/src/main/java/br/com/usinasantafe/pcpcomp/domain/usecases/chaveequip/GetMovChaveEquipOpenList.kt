package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.presenter.chaveequip.model.ControleChaveEquipModel
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovChaveEquipOpenList {
    suspend operator fun invoke(): Result<List<ControleChaveEquipModel>>
}

class IGetMovChaveEquipOpenList(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val colabRepository: ColabRepository,
    private val equipRepository: EquipRepository
): GetMovChaveEquipOpenList {

    override suspend fun invoke(): Result<List<ControleChaveEquipModel>> {
        try{
            val resultList = movChaveEquipRepository.listOpen()
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val entityList = resultList.getOrNull()!!.map {
                val resultNomeColab = colabRepository.getNome(it.matricColabMovChaveEquip!!)
                if (resultNomeColab.isFailure)
                    return Result.failure(resultNomeColab.exceptionOrNull()!!)
                val nomeColab = resultNomeColab.getOrNull()!!
                val resultGetEquip = equipRepository.getDescr(it.idEquipMovChaveEquip!!)
                if (resultGetEquip.isFailure)
                    return Result.failure(resultGetEquip.exceptionOrNull()!!)
                val descrEquip = resultGetEquip.getOrNull()!!
                ControleChaveEquipModel(
                    id = it.idMovChaveEquip!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovChaveEquip),
                    tipoMov = when (it.tipoMovChaveEquip!!) {
                        TypeMovKey.REMOVE -> "RETIRADA"
                        TypeMovKey.RECEIPT -> "DEVOLUÇÃO"
                    },
                    equip = descrEquip,
                    colab = "${it.matricColabMovChaveEquip} - $nomeColab"
                )
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IGetMovChaveEquipOpenList",
                    cause = e
                )
            )
        }
    }

}