package br.com.usinasantafe.pcpcomp.infra.models.webservice

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import kotlinx.serialization.Serializable

@Serializable
data class ConfigWebServiceModelOutput(
    val number: Long,
    val version: String,
)

@Serializable
data class ConfigWebServiceModelInput(
    var idBD: Long,
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