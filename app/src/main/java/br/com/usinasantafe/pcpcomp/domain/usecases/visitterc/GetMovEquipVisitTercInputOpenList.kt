package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.presenter.visitterc.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipVisitTercInputOpenList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTercModel>>
}

class GetMovEquipVisitTercInputOpenListImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val getMotorista: GetMotorista
) : GetMovEquipVisitTercInputOpenList {

    override suspend fun invoke(): Result<List<MovEquipVisitTercModel>> {
        try {
            val resultList = movEquipVisitTercRepository.listOpenInput()
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                val resultTipo = movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = it.idMovEquipVisitTerc!!
                )
                if (resultTipo.isFailure)
                    return Result.failure(resultTipo.exceptionOrNull()!!)
                val typeVisitTerc = resultTipo.getOrNull()!!
                val resultMotorista = getMotorista(
                    typeVisitTerc = typeVisitTerc,
                    idVisitTerc = it.idVisitTercMovEquipVisitTerc!!
                )
                if (resultMotorista.isFailure)
                    return Result.failure(resultMotorista.exceptionOrNull()!!)
                val motorista = resultMotorista.getOrNull()!!
                MovEquipVisitTercModel(
                    id = it.idMovEquipVisitTerc!!,
                    dthr = "DATA/HORA: ${
                        SimpleDateFormat(
                            "dd/MM/yyyy HH:mm",
                            Locale("pt", "BR")
                        ).format(it.dthrMovEquipVisitTerc)}",
                    veiculo = it.veiculoMovEquipVisitTerc!!,
                    placa = it.placaMovEquipVisitTerc!!,
                    tipoVisitTerc = typeVisitTerc.name,
                    motorista = motorista
                )
            }
            return Result.success(modelList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetMovEquipVisitTercInputOpenListImpl",
                    cause = e
                )
            )
        }
    }

}