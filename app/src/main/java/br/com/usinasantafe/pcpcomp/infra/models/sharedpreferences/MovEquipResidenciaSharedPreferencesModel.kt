package br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import java.util.Date

data class MovEquipResidenciaSharedPreferencesModel(
    var dthrMovEquipResidencia: Date = Date(),
    var tipoMovEquipResidencia: TypeMovEquip = TypeMovEquip.INPUT,
    var motoristaMovEquipResidencia: String? = null,
    var veiculoMovEquipResidencia: String? = null,
    var placaMovEquipResidencia: String? = null,
    var observMovEquipResidencia: String? = null,
)

fun MovEquipResidenciaSharedPreferencesModel.entityToSharedPreferencesModel(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            dthrMovEquipResidencia = this.dthrMovEquipResidencia,
            tipoMovEquipResidencia = this.tipoMovEquipResidencia,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia,
            placaMovEquipResidencia = this.placaMovEquipResidencia,
            observMovEquipResidencia = this.observMovEquipResidencia,
        )
    }
}

fun MovEquipResidencia.entityToSharedPreferencesModel(): MovEquipResidenciaSharedPreferencesModel {
    return with(this){
        MovEquipResidenciaSharedPreferencesModel(
            dthrMovEquipResidencia = this.dthrMovEquipResidencia,
            tipoMovEquipResidencia = this.tipoMovEquipResidencia!!,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia,
            placaMovEquipResidencia = this.placaMovEquipResidencia,
            observMovEquipResidencia = this.observMovEquipResidencia,
        )
    }
}
