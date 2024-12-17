package br.com.usinasantafe.pcpcomp.domain.entities.variable

import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.util.Date

data class MovChave(
    var idMovChave: Int? = null,
    var matricVigiaMovChave: Int? = null,
    var idLocalMovChave: Int? = null,
    var tipoMovChave: TypeMovKey? = null,
    var dthrMovChave: Date = Date(),
    var idChaveMovChave: Int? = null,
    var matricColabMovChave: Int? = null,
    var observMovChave: String? = null,
    var statusMovChave: StatusData = StatusData.OPEN,
    var statusSendMovChave: StatusSend = StatusSend.SEND,
    var statusForeignerMovChave: StatusForeigner = StatusForeigner.INSIDE,
)
