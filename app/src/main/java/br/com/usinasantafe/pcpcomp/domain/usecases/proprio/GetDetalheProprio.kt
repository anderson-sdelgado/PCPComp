package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe.DetalheProprioModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheProprioModel>
}

class IGetDetalheProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val equipRepository: EquipRepository,
    private val colabRepository: ColabRepository
) : GetDetalheProprio {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheProprioModel> {
        try {
            val resultGet = movEquipProprioRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val mov = resultGet.getOrNull()!!

            val dthr = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(mov.dthrMovEquipProprio)
            val tipoMov = if (mov.tipoMovEquipProprio!!.ordinal == 0) "ENTRADA" else "SAÍDA"

            val resultNro = equipRepository.getDescr(mov.idEquipMovEquipProprio!!)
            if (resultNro.isFailure)
                return Result.failure(resultNro.exceptionOrNull()!!)
            val veiculo = resultNro.getOrNull()!!

            val resultEquipSegList =
                movEquipProprioEquipSegRepository.list(FlowApp.CHANGE, mov.idMovEquipProprio!!)
            if (resultEquipSegList.isFailure)
                return Result.failure(resultEquipSegList.exceptionOrNull()!!)
            val equipSegList = resultEquipSegList.getOrNull()!!
            var veicSeg = ""
            for (equipSeg in equipSegList) {
                val resultNroEquipSeg = equipRepository.getNro(equipSeg.idEquip!!)
                if (resultNroEquipSeg.isFailure)
                    return Result.failure(resultNroEquipSeg.exceptionOrNull()!!)
                val nroEquipSeg = resultNroEquipSeg.getOrNull()!!
                veicSeg += "$nroEquipSeg - "
            }

            val resultGetNome = colabRepository.getNome(mov.matricColabMovEquipProprio!!)
            if (resultGetNome.isFailure)
                return Result.failure(resultGetNome.exceptionOrNull()!!)
            val nome = resultGetNome.getOrNull()!!
            val motorista = "${mov.matricColabMovEquipProprio!!} - $nome"

            val resultPassagList =
                movEquipProprioPassagRepository.list(
                    FlowApp.CHANGE,
                    mov.idMovEquipProprio!!
                )
            if (resultPassagList.isFailure)
                return Result.failure(resultPassagList.exceptionOrNull()!!)
            val passagList = resultPassagList.getOrNull()!!
            var passageiro = ""
            for (passag in passagList) {
                val resultGetNomePassag = colabRepository.getNome(passag.matricColab!!)
                if (resultGetNomePassag.isFailure)
                    return Result.failure(resultGetNomePassag.exceptionOrNull()!!)
                passageiro += "${passag.matricColab!!} - ${resultGetNomePassag.getOrNull()!!}; "
            }

            val destino = "${mov.destinoMovEquipProprio}"

            val descrNotaFiscal =
                if (mov.notaFiscalMovEquipProprio == null) "" else mov.notaFiscalMovEquipProprio
            val notaFiscal = "$descrNotaFiscal"

            val observ =
                if (mov.observMovEquipProprio.isNullOrEmpty()) "" else mov.observMovEquipProprio

            return Result.success(
                DetalheProprioModel(
                    dthr = dthr,
                    tipoMov = tipoMov,
                    veiculo = veiculo,
                    veiculoSec = veicSeg,
                    motorista = motorista,
                    passageiro = passageiro,
                    destino = destino,
                    notaFiscal = notaFiscal,
                    observ = observ
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverDetalheProprioImpl",
                    cause = e
                )
            )
        }
    }

}