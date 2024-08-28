package br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date


data class MovEquipProprioSharedPreferencesModel(
    var dthrMovEquipProprio: Date = Date(),
    var tipoMovEquipProprio: TypeMov,
    var idEquipMovEquipProprio: Int? = null,
    var nroMatricColabMovEquipProprio: Int? = null,
    var destinoMovEquipProprio: String? = null,
    var nroNotaFiscalMovEquipProprio: Int? = null,
    var observMovEquipProprio: String? = null,
)

fun MovEquipProprioSharedPreferencesModel.sharedPreferencesModelToEntity(): MovEquipProprio {
    return with(this){
        MovEquipProprio(
            dthrMovEquipProprio = this.dthrMovEquipProprio,
            tipoMovEquipProprio = this.tipoMovEquipProprio,
            idEquipMovEquipProprio = this.idEquipMovEquipProprio,
            nroMatricColabMovEquipProprio = this.nroMatricColabMovEquipProprio,
            destinoMovEquipProprio = this.destinoMovEquipProprio,
            nroNotaFiscalMovEquipProprio = this.nroNotaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
        )
    }
}