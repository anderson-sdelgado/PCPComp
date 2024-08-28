package br.com.usinasantafe.pcpcomp.domain.entities.variable

import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date

data class MovEquipProprio(
    var idMovEquipProprio: Int? = null,
    var tipoMovEquipProprio: TypeMov? = null,
    var idEquipMovEquipProprio: Int? = null,
    var idLocalMovEquipProprio: Int? = null,
    var dthrMovEquipProprio: Date = Date(),
    var nroMatricVigiaMovEquipProprio: Int? = null,
    var nroMatricColabMovEquipProprio: Int? = null,
    var destinoMovEquipProprio: String? = null,
    var nroNotaFiscalMovEquipProprio: Int? = null,
    var observMovEquipProprio: String? = null,
    var statusMovEquipProprio: StatusData = StatusData.OPEN,
    var statusSendMovEquipProprio: StatusSend = StatusSend.SEND,
    var movEquipProprioEquipSegList: List<MovEquipProprioEquipSeg>? = emptyList(),
    var movEquipProprioPassagList: List<MovEquipProprioPassag>? = emptyList(),
)
