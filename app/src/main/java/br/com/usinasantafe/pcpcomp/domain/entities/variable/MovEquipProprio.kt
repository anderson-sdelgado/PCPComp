package br.com.usinasantafe.pcpcomp.domain.entities.variable

import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date

data class MovEquipProprio(
    var idMovEquipProprio: Long? = null,
    var tipoMovEquipProprio: TypeMov? = null,
    var idEquipMovEquipProprio: Long? = null,
    var idLocalMovEquipProprio: Long? = null,
    var dthrMovEquipProprio: Date = Date(),
    var nroMatricVigiaMovEquipProprio: Long? = null,
    var nroMatricColabMovEquipProprio: Long? = null,
    var destinoMovEquipProprio: String? = null,
    var nroNotaFiscalMovEquipProprio: Long? = null,
    var observMovEquipProprio: String? = null,
    var statusSendMovEquipProprio: StatusSend = StatusSend.STARTED,
    var movEquipProprioSegList: List<MovEquipProprioSeg>? = emptyList(),
    var movEquipProprioPassagList: List<MovEquipProprioPassag>? = emptyList(),
)
