package br.com.usinasantafe.pcpcomp.domain.entities

import br.com.usinasantafe.pcpcomp.utils.Errors

data class ResultUpdate(
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELDEMPTY,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)