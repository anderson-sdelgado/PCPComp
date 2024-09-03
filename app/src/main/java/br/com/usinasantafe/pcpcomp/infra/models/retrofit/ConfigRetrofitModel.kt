package br.com.usinasantafe.pcpcomp.infra.models.retrofit

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config

data class ConfigWebServiceModelOutput(
    val number: Long,
    val version: String,
)

data class ConfigWebServiceModelInput(
    var idBD: Int,
)


fun Config.toConfigWebServiceModel(): ConfigWebServiceModelOutput {
    return with(this){
        ConfigWebServiceModelOutput(
            number = this.number!!,
            version = this.version!!,
        )
    }
}

fun ConfigWebServiceModelInput.toConfig(): Config {
    return with(this){
        Config(
            idBD = this.idBD
        )
    }
}