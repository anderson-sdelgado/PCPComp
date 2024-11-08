package br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import java.text.SimpleDateFormat
import java.util.Locale

data class MovEquipResidenciaRetrofitModelOutput(
    var idMovEquipResidencia: Int,
    var nroAparelhoMovEquipResidencia: Long,
    var nroMatricVigiaMovEquipResidencia: Int,
    var idLocalMovEquipResidencia: Int,
    var dthrMovEquipResidencia:  String,
    var tipoMovEquipResidencia: Int,
    var motoristaMovEquipResidencia: String,
    var veiculoMovEquipResidencia: String,
    var placaMovEquipResidencia: String,
    var observMovEquipResidencia: String?,
)

data class MovEquipResidenciaRetrofitModelInput(
    var idMovEquipResidencia: Int
)

fun MovEquipResidencia.entityToRetrofitModelOutput(nroAparelho: Long): MovEquipResidenciaRetrofitModelOutput {
    return with(this){
        MovEquipResidenciaRetrofitModelOutput(
            idMovEquipResidencia = this.idMovEquipResidencia!!,
            nroAparelhoMovEquipResidencia = nroAparelho,
            nroMatricVigiaMovEquipResidencia = this.nroMatricVigiaMovEquipResidencia!!,
            idLocalMovEquipResidencia = this.idLocalMovEquipResidencia!!,
            dthrMovEquipResidencia = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovEquipResidencia),
            tipoMovEquipResidencia = this.tipoMovEquipResidencia!!.ordinal + 1,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia!!,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia!!,
            placaMovEquipResidencia = this.placaMovEquipResidencia!!,
            observMovEquipResidencia = this.observMovEquipResidencia,
        )
    }
}

fun MovEquipResidenciaRetrofitModelInput.retrofitModelInputToEntity(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            idMovEquipResidencia = this.idMovEquipResidencia,
        )
    }
}
