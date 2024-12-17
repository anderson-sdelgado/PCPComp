package br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.util.Date

data class MovChaveSharedPreferencesModel(
    var dthrMovChave: Date = Date(),
    var tipoMovChave: TypeMovKey? =TypeMovKey.REMOVE,
    var idChaveMovChave: Int? = null,
    var matricColabMovChave: Int? = null,
    var observMovChave: String? = null,
)

fun MovChaveSharedPreferencesModel.entityToSharedPreferencesModel(): MovChave {
    return with(this) {
        MovChave(
            dthrMovChave = this.dthrMovChave,
            tipoMovChave = this.tipoMovChave,
            idChaveMovChave = this.idChaveMovChave,
            matricColabMovChave = this.matricColabMovChave,
            observMovChave = this.observMovChave,
        )
    }
}

fun MovChave.entityToSharedPreferencesModel(): MovChaveSharedPreferencesModel {
    return with(this) {
        MovChaveSharedPreferencesModel(
            dthrMovChave = this.dthrMovChave,
            tipoMovChave = this.tipoMovChave,
            idChaveMovChave = this.idChaveMovChave,
            matricColabMovChave = this.matricColabMovChave,
            observMovChave = this.observMovChave,
        )
    }
}