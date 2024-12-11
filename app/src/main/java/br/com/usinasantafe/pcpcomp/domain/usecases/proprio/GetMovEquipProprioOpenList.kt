package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.presenter.proprio.movlist.MovEquipProprioModel
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipProprioOpenList {
    suspend operator fun invoke(): Result<List<MovEquipProprioModel>>
}

class IGetMovEquipProprioOpenList(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val equipRepository: EquipRepository,
    private val colabRepository: ColabRepository
): GetMovEquipProprioOpenList {

    override suspend fun invoke(): Result<List<MovEquipProprioModel>> {
        try {
            val resultList = movEquipProprioRepository.listOpen()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                val resultNro = equipRepository.getNro(it.idEquipMovEquipProprio!!)
                if(resultNro.isFailure)
                    return Result.failure(resultNro.exceptionOrNull()!!)
                val nroEquip = resultNro.getOrNull()!!
                val resultNome = colabRepository.getNome(it.matricColabMovEquipProprio!!)
                if(resultNome.isFailure)
                    return Result.failure(resultNome.exceptionOrNull()!!)
                val nomeColab = resultNome.getOrNull()!!
                MovEquipProprioModel(
                    id = it.idMovEquipProprio!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovEquipProprio),
                    typeMov = if (it.tipoMovEquipProprio == TypeMovEquip.INPUT) "ENTRADA" else "SAIDA",
                    equip = "$nroEquip",
                    colab = " ${it.matricColabMovEquipProprio!!} - $nomeColab"
                )
            }
            return Result.success(modelList)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "RecoverMovEquipProprioOpenList",
                    cause = e
                )
            )
        }
    }

}