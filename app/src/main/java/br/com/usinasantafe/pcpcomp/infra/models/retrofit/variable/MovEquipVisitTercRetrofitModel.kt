package br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import java.text.SimpleDateFormat
import java.util.Locale

data class MovEquipVisitTercRetrofitModelOutput(
    var idMovEquipVisitTerc: Int,
    var nroAparelhoMovEquipVisitTerc: Long,
    var nroMatricVigiaMovEquipVisitTerc: Int,
    var idLocalMovEquipVisitTerc: Int,
    var dthrMovEquipVisitTerc: String,
    var tipoMovEquipVisitTerc: Int,
    var idVisitTercMovEquipVisitTerc: Int,
    var tipoVisitTercMovEquipVisitTerc: Int,
    var veiculoMovEquipVisitTerc: String,
    var placaMovEquipVisitTerc: String,
    var destinoMovEquipVisitTerc: String?,
    var observMovEquipVisitTerc: String?,
    var movEquipVisitTercPassagList: List<MovEquipVisitTercPassagRetrofitModelOutput>?,
)

data class MovEquipVisitTercRetrofitModelInput(
    val idMovEquipVisitTerc: Int
)

fun MovEquipVisitTerc.entityToRetrofitModelOutput(nroAparelho: Long): MovEquipVisitTercRetrofitModelOutput {
    return with(this){
        MovEquipVisitTercRetrofitModelOutput(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc!!,
            nroAparelhoMovEquipVisitTerc = nroAparelho,
            nroMatricVigiaMovEquipVisitTerc = this.nroMatricVigiaMovEquipVisitTerc!!,
            idLocalMovEquipVisitTerc = this.idLocalMovEquipVisitTerc!!,
            dthrMovEquipVisitTerc = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovEquipVisitTerc),
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc!!.ordinal + 1,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc!!,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc!!.ordinal + 1,
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc!!,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc!!,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            movEquipVisitTercPassagList = this.movEquipVisitTercPassagList?.map { it.entityToRetrofitModel() }
        )
    }
}

fun MovEquipVisitTercRetrofitModelInput.retrofitModelInputToEntity(): MovEquipVisitTerc {
    return with(this){
        MovEquipVisitTerc(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
        )
    }
}