package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.presenter.chaveequip.detalhe.DetalheChaveEquipModel
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<DetalheChaveEquipModel>
}

class IGetDetalheMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val colabRepository: ColabRepository,
    private val equipRepository: EquipRepository
): GetDetalheMovChaveEquip {

    override suspend fun invoke(id: Int): Result<DetalheChaveEquipModel> {
        try {
            val resultMovChave = movChaveEquipRepository.get(id)
            if (resultMovChave.isFailure)
                return Result.failure(resultMovChave.exceptionOrNull()!!)
            val entity = resultMovChave.getOrNull()!!
            val resultNomeColab = colabRepository.getNome(entity.matricColabMovChaveEquip!!)
            if (resultNomeColab.isFailure)
                return Result.failure(resultNomeColab.exceptionOrNull()!!)
            val nomeColab = resultNomeColab.getOrNull()!!
            val resultGetEquip = equipRepository.getDescr(entity.idEquipMovChaveEquip!!)
            if (resultGetEquip.isFailure)
                return Result.failure(resultGetEquip.exceptionOrNull()!!)
            val descrEquip = resultGetEquip.getOrNull()!!
            return Result.success(
                DetalheChaveEquipModel(
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(entity.dthrMovChaveEquip),
                    tipoMov = when (entity.tipoMovChaveEquip!!) {
                        TypeMovKey.REMOVE -> "RETIRADA"
                        TypeMovKey.RECEIPT -> "DEVOLUÇÃO"
                    },
                    equip = descrEquip,
                    colab = "${entity.matricColabMovChaveEquip} - $nomeColab",
                    observ = entity.observMovChaveEquip
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IGetDetalheMovChaveEquip",
                    cause = e
                )
            )
        }
    }

}