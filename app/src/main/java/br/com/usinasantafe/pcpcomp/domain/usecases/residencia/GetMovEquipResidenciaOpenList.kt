package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.presenter.residencia.model.MovEquipResidenciaModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipResidenciaOpenList {
    suspend operator fun invoke(): Result<List<MovEquipResidenciaModel>>
}

class GetMovEquipResidenciaOpenListImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): GetMovEquipResidenciaOpenList {

    override suspend fun invoke(): Result<List<MovEquipResidenciaModel>> {
        try {
            val resultList = movEquipResidenciaRepository.listOpen()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                MovEquipResidenciaModel(
                    id = it.idMovEquipResidencia!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovEquipResidencia),
                    veiculo = it.veiculoMovEquipResidencia!!,
                    placa = it.placaMovEquipResidencia!!,
                    motorista = it.motoristaMovEquipResidencia!!,
                    tipoMov = if (it.tipoMovEquipResidencia == TypeMov.INPUT) "ENTRADA" else "SAIDA",
                )
            }
            return Result.success(modelList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetMovEquipResidenciaOpenListImpl",
                    cause = e
                )
            )
        }
    }

}