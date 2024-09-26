package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe.DetalheResidenciaModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheResidenciaModel>
}

class GetDetalheResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetDetalheResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheResidenciaModel> {
        try {
            val resultGet = movEquipResidenciaRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val mov = resultGet.getOrNull()!!
            val dthr = "DATA/HORA: ${
                SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(mov.dthrMovEquipResidencia)
            }"
            val tipoMov = if (mov.tipoMovEquipResidencia!!.ordinal == 0) "ENTRADA" else "SAÍDA"
            val veiculo = "VEÍCULO: ${mov.veiculoMovEquipResidencia}"
            val placa = "PLACA: ${mov.placaMovEquipResidencia}"
            val motorista = "MOTORISTA: ${mov.motoristaMovEquipResidencia}"
            val descrObserv =
                if (mov.observMovEquipResidencia.isNullOrEmpty()) "" else mov.observMovEquipResidencia
            val observ = "OBSERVAÇÕES: $descrObserv"
            return Result.success(
                DetalheResidenciaModel(
                    id = id,
                    dthr = dthr,
                    tipoMov = tipoMov,
                    veiculo = veiculo,
                    placa = placa,
                    motorista = motorista,
                    observ = observ
                )
            )
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "GetDetalheResidenciaImpl",
                    cause = e
                )
            )
        }

    }

}