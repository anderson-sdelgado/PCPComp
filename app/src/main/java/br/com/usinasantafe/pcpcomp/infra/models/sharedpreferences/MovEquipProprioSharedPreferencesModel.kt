package br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date


data class MovEquipProprioSharedPreferencesModel(
    var dthrMovEquipProprio: Date = Date(),
    var tipoMovEquipProprio: TypeMov,
    var idEquipMovEquipProprio: Long? = null,
    var nroMatricColabMovEquipProprio: Long? = null,
    var destinoMovEquipProprio: String? = null,
    var nroNotaFiscalMovEquipProprio: Long? = null,
    var observMovEquipProprio: String? = null,
)

fun MovEquipProprioSharedPreferencesModel.modelSharedPreferencesToMovEquipProprio(): MovEquipProprio {
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
