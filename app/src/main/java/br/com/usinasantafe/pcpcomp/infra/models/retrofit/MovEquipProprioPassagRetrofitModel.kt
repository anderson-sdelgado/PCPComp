package br.com.usinasantafe.pcpcomp.infra.models.retrofit

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioPassag

data class MovEquipProprioPassagWebServiceModelOutput(
    var idMovEquipProprioPassag: Int,
    var idMovEquipProprio: Int,
    var matricColab: Int,
)

fun MovEquipProprioPassag.entityToWebServiceModel(): MovEquipProprioPassagWebServiceModelOutput {
    return with(this){
        MovEquipProprioPassagWebServiceModelOutput(
            idMovEquipProprioPassag = this.idMovEquipProprioPassag!!,
            idMovEquipProprio = this.idMovEquipProprio!!,
            matricColab = this.matricColab!!,
        )
    }
}