package br.com.usinasantafe.pcpcomp.domain.entities.variable

import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import java.util.Date

data class MovEquipVisitTerc(
    var idMovEquipVisitTerc: Int? = null,
    var nroAparelhoMovEquipVisitTerc: Int? = null,
    var nroMatricVigiaMovEquipVisitTerc: Int? = null,
    var idLocalMovEquipVisitTerc: Int? = null,
    var dthrMovEquipVisitTerc: Date = Date(),
    var tipoMovEquipVisitTerc: TypeMovEquip? = null,
    var idVisitTercMovEquipVisitTerc: Int? = null,
    var tipoVisitTercMovEquipVisitTerc: TypeVisitTerc? = null,
    var veiculoMovEquipVisitTerc: String? = null,
    var placaMovEquipVisitTerc: String? = null,
    var destinoMovEquipVisitTerc: String? = null,
    var observMovEquipVisitTerc: String? = null,
    var statusMovEquipVisitTerc: StatusData = StatusData.OPEN,
    var statusSendMovEquipVisitTerc: StatusSend = StatusSend.SEND,
    var statusMovEquipForeignerVisitTerc: StatusForeigner = StatusForeigner.INSIDE,
    var movEquipVisitTercPassagList: List<MovEquipVisitTercPassag>? = emptyList(),
)
