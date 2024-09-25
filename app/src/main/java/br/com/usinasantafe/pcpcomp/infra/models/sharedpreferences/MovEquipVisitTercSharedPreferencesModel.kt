package br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import java.util.Date

data class MovEquipVisitTercSharedPreferencesModel(
    var dthrMovEquipVisitTerc: Date = Date(),
    var tipoMovEquipVisitTerc: TypeMov = TypeMov.INPUT,
    var idVisitTercMovEquipVisitTerc: Int? = null,
    var tipoVisitTercMovEquipVisitTerc: TypeVisitTerc? = null,
    var veiculoMovEquipVisitTerc: String? = null,
    var placaMovEquipVisitTerc: String? = null,
    var destinoMovEquipVisitTerc: String? = null,
    var observMovEquipVisitTerc: String? = null,
)

fun MovEquipVisitTercSharedPreferencesModel.sharedPreferencesModelToEntity(): MovEquipVisitTerc {
    return with(this){
        MovEquipVisitTerc(
            dthrMovEquipVisitTerc = this.dthrMovEquipVisitTerc,
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc,
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
        )
    }
}
