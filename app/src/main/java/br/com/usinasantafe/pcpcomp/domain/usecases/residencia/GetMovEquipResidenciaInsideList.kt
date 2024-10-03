package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.presenter.residencia.model.MovEquipResidenciaModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipResidenciaInsideList {
    suspend operator fun invoke(): Result<List<MovEquipResidenciaModel>>
}

class GetMovEquipResidenciaInsideListImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): GetMovEquipResidenciaInsideList {

    override suspend fun invoke(): Result<List<MovEquipResidenciaModel>> {
        try {
            val resultList = movEquipResidenciaRepository.listInside()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                MovEquipResidenciaModel(
                    id = it.idMovEquipResidencia!!,
                    dthr = "DATA/HORA: ${
                        SimpleDateFormat(
                            "dd/MM/yyyy HH:mm",
                            Locale("pt", "BR")
                        ).format(it.dthrMovEquipResidencia)}",
                    veiculo = "VEICULO: ${it.veiculoMovEquipResidencia!!}",
                    placa = "PLACA: ${it.placaMovEquipResidencia!!}",
                    motorista = "MOTORISTA: ${it.motoristaMovEquipResidencia!!}"
                )
            }
            return Result.success(modelList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetMovEquipResidenciaInputOpenListImpl",
                    cause = e
                )
            )
        }
    }

}