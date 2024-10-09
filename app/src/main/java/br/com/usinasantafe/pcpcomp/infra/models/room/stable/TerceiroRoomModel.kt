package br.com.usinasantafe.pcpcomp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.utils.TB_TERCEIRO
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro

@Entity(tableName = TB_TERCEIRO)
data class TerceiroRoomModel (
    @PrimaryKey(autoGenerate = true)
    val idTerceiro: Int,
    val idBDTerceiro: Int,
    val cpfTerceiro: String,
    val nomeTerceiro: String,
    val empresaTerceiro: String,
)

fun TerceiroRoomModel.roomModelToEntity(): Terceiro {
    return with(this){
        Terceiro(
            idTerceiro = this.idTerceiro,
            idBDTerceiro = this.idBDTerceiro,
            cpfTerceiro = this.cpfTerceiro,
            nomeTerceiro = this.nomeTerceiro,
            empresaTerceiro = this.empresaTerceiro,
        )
    }
}

fun Terceiro.entityToRoomModel(): TerceiroRoomModel{
    return with(this){
        TerceiroRoomModel(
            idTerceiro = this.idTerceiro,
            idBDTerceiro = this.idBDTerceiro,
            cpfTerceiro = this.cpfTerceiro,
            nomeTerceiro = this.nomeTerceiro,
            empresaTerceiro = this.empresaTerceiro,
        )
    }
}