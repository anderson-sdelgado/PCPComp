package br.com.usinasantafe.pcpcomp.domain.entities.variable

import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.util.Date

data class MovChaveEquip(
    var idMovChaveEquip: Int? = null,
    var dthrMovChaveEquip: Date = Date(),
    var tipoMovChaveEquip: TypeMovKey? = null,
    var nroAparelhoMovChaveEquip: Int? = null,
    var nroMatricVigiaMovChaveEquip: Int? = null,
    var idLocalMovChaveEquip: Int? = null,
    var idEquipMovChaveEquip: Int? = null,
    var matricColabMovChaveEquip: Int? = null,
    var observMovChaveEquip: String? = null,
    var statusMovChaveEquip: StatusData = StatusData.OPEN,
    var statusSendMovChaveEquip: StatusSend = StatusSend.SEND,
    var statusForeignerMovChaveEquip: StatusForeigner = StatusForeigner.INSIDE,
)
