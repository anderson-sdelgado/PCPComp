package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe.DetalheVisitTercModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheVisitTercModel>
}

class GetDetalheVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val getMotoristaVisitTerc: GetMotoristaVisitTerc,
) : GetDetalheVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheVisitTercModel> {
        try {
            val resultGet = movEquipVisitTercRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val mov = resultGet.getOrNull()!!
            val dthr = "DATA/HORA: ${
                SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(mov.dthrMovEquipVisitTerc)
            }"
            val tipoMov = if (mov.tipoMovEquipVisitTerc!!.ordinal == 0) "ENTRADA" else "SAÍDA"
            val veiculo = "VEÍCULO: ${mov.veiculoMovEquipVisitTerc}"
            val placa = "PLACA: ${mov.placaMovEquipVisitTerc}"
            val tipoVisitTerc = when (mov.tipoVisitTercMovEquipVisitTerc!!) {
                TypeVisitTerc.VISITANTE -> "VISITANTE"
                TypeVisitTerc.TERCEIRO -> "TERCEIRO"
            }
            val resultGetMotorista = getMotoristaVisitTerc(
                mov.tipoVisitTercMovEquipVisitTerc!!,
                mov.idVisitTercMovEquipVisitTerc!!
            )
            if (resultGetMotorista.isFailure)
                return Result.failure(resultGetMotorista.exceptionOrNull()!!)
            val motorista = "MOTORISTA: ${resultGetMotorista.getOrNull()!!}"
            var passageiro = "PASSAGEIROS: "

            val resultPassagList =
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    mov.idMovEquipVisitTerc!!
                )
            if (resultPassagList.isFailure)
                return Result.failure(resultPassagList.exceptionOrNull()!!)
            val passagList = resultPassagList.getOrNull()!!
            for (passag in passagList) {
                val resultGetPassag = getMotoristaVisitTerc(
                    mov.tipoVisitTercMovEquipVisitTerc!!,
                    passag.idVisitTerc!!
                )
                if (resultGetPassag.isFailure)
                    return Result.failure(resultGetPassag.exceptionOrNull()!!)
                passageiro += "${resultGetPassag.getOrNull()!!}; "
            }

            val descrDestino =
                if (mov.destinoMovEquipVisitTerc.isNullOrEmpty()) "" else mov.destinoMovEquipVisitTerc
            val destino = "DESTINO: $descrDestino"
            val descrObserv =
                if (mov.observMovEquipVisitTerc.isNullOrEmpty()) "" else mov.observMovEquipVisitTerc
            val observ = "OBSERVAÇÕES: $descrObserv"

            return Result.success(
                DetalheVisitTercModel(
                    dthr = dthr,
                    tipoMov = tipoMov,
                    veiculo = veiculo,
                    placa = placa,
                    motorista = motorista,
                    passageiro = passageiro,
                    destino = destino,
                    observ = observ,
                    tipoVisitTerc = tipoVisitTerc
                )
            )

        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetDetalheVisitTercImpl",
                    cause = e
                )
            )
        }
    }

}