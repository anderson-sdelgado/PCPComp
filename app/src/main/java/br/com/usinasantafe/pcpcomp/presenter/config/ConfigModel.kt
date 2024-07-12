package br.com.usinasantafe.pcpcomp.presenter.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config

data class ConfigModel(
    val number: String,
    val password: String,
)

fun Config.toConfigModel(): ConfigModel {
    return with(this){
        ConfigModel(
            number = this.numberConfig!!.toString(),
            password = this.passwordConfig!!,
        )
    }
}
