package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.presenter.proprio.movlist.MovEquipProprioModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipProprioOpenList {
    suspend operator fun invoke(): Result<List<MovEquipProprioModel>>
}

class GetMovEquipProprioOpenListImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val equipRepository: EquipRepository,
    private val colabRepository: ColabRepository
): GetMovEquipProprioOpenList {

    override suspend fun invoke(): Result<List<MovEquipProprioModel>> {
        try {
            val resultListOpen = movEquipProprioRepository.listOpen()
            if(resultListOpen.isFailure)
                return Result.failure(resultListOpen.exceptionOrNull()!!)
            val listOpen = resultListOpen.getOrNull()!!
            return Result.success(
                listOpen.map {
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
                        dthr = "DATA/HORA: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(it.dthrMovEquipProprio)}",
                        typeMov = if (it.tipoMovEquipProprio == TypeMov.INPUT) "ENTRADA" else "SAIDA",
                        equip = "VEICULO: $nroEquip",
                        colab = "MOTORISTA: ${it.matricColabMovEquipProprio!!} - $nomeColab"
                    )
                }
            )
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