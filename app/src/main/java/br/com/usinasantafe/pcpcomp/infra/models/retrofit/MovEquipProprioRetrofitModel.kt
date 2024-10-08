package br.com.usinasantafe.pcpcomp.infra.models.retrofit

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import java.text.SimpleDateFormat
import java.util.Locale

data class MovEquipProprioRetrofitModelOutput(
    var idMovEquipProprio: Int,
    var nroAparelhoMovEquipProprio: Long,
    var matricVigiaMovEquipProprio: Int,
    var idLocalMovEquipProprio: Int,
    var dthrMovEquipProprio: String,
    var tipoMovEquipProprio: Int,
    var idEquipMovEquipProprio: Int,
    var matricColabMovEquipProprio: Int,
    var destinoMovEquipProprio: String,
    var notaFiscalMovEquipProprio: Int?,
    var observMovEquipProprio: String?,
    var movEquipProprioEquipSegList: List<MovEquipProprioEquipSegWebServiceModelOutput>?,
    var movEquipProprioPassagList: List<MovEquipProprioPassagRetrofitModelOutput>?,
)

data class MovEquipProprioRetrofitModelInput(
    val idMovEquipProprio: Int
)

fun MovEquipProprio.entityToRetrofitModelOutput(nroAparelho: Long): MovEquipProprioRetrofitModelOutput {
    return with(this){
        MovEquipProprioRetrofitModelOutput(
            idMovEquipProprio = this.idMovEquipProprio!!,
            nroAparelhoMovEquipProprio = nroAparelho,
            matricVigiaMovEquipProprio = this.matricVigiaMovEquipProprio!!,
            idLocalMovEquipProprio = this.idLocalMovEquipProprio!!,
            dthrMovEquipProprio = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovEquipProprio),
            tipoMovEquipProprio = this.tipoMovEquipProprio!!.ordinal + 1,
            idEquipMovEquipProprio = this.idEquipMovEquipProprio!!,
            matricColabMovEquipProprio = this.matricColabMovEquipProprio!!,
            destinoMovEquipProprio = this.destinoMovEquipProprio!!,
            notaFiscalMovEquipProprio = this.notaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
            movEquipProprioEquipSegList = this.movEquipProprioEquipSegList?.map { it.entityToRetrofitModel() },
            movEquipProprioPassagList = this.movEquipProprioPassagList?.map { it.entityToRetrofitModel() }
        )
    }
}

fun MovEquipProprioRetrofitModelInput.retrofitModelInputToEntity(): MovEquipProprio {
    return with(this){
        MovEquipProprio(
            idMovEquipProprio = this.idMovEquipProprio,
        )
    }
}