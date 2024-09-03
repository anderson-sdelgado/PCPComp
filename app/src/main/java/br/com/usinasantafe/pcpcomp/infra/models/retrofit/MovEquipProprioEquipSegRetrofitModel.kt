package br.com.usinasantafe.pcpcomp.infra.models.retrofit

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioEquipSeg

data class MovEquipProprioEquipSegWebServiceModelOutput(
    var idMovEquipProprioSeg: Int,
    var idMovEquipProprio: Int,
    var idEquip: Int,
)


fun MovEquipProprioEquipSeg.entityToWebServiceModel(): MovEquipProprioEquipSegWebServiceModelOutput {
    return with(this){
        MovEquipProprioEquipSegWebServiceModelOutput(
            idMovEquipProprioSeg = this.idMovEquipProprioEquipSeg!!,
            idMovEquipProprio = this.idMovEquipProprio!!,
            idEquip = this.idEquip!!,
        )
    }
}