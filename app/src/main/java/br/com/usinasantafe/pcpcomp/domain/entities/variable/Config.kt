package br.com.usinasantafe.pcpcomp.domain.entities.variable

import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend

data class Config(
    var numberConfig: Long? = null,
    var passwordConfig: String? = null,
    var idBDConfig: Long? = null,
    var version: String? = null,
    var flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
    var matricVigia: Long? = null,
    var idLocal: Long? = null,
    var statusEnvio: StatusSend = StatusSend.SENT,
    var statusApont: StatusData = StatusData.CLOSE,
)
