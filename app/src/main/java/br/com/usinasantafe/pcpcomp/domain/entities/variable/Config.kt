package br.com.usinasantafe.pcpcomp.domain.entities.variable

import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend

data class Config(
    var number: Long? = null,
    var password: String? = null,
    var idBD: Long? = null,
    var version: String? = null,
    var flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
    var matricVigia: Long? = null,
    var idLocal: Long? = null,
    var statusSend: StatusSend = StatusSend.SENT,
    var statusData: StatusData = StatusData.CLOSE,
)
