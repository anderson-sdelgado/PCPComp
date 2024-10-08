package br.com.usinasantafe.pcpcomp.infra.models.retrofit

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioPassag

data class MovEquipProprioPassagRetrofitModelOutput(
    var idMovEquipProprioPassag: Int,
    var idMovEquipProprio: Int,
    var matricColab: Int,
)

fun MovEquipProprioPassag.entityToRetrofitModel(): MovEquipProprioPassagRetrofitModelOutput {
    return with(this){
        MovEquipProprioPassagRetrofitModelOutput(
            idMovEquipProprioPassag = this.idMovEquipProprioPassag!!,
            idMovEquipProprio = this.idMovEquipProprio!!,
            matricColab = this.matricColab!!,
        )
    }
}