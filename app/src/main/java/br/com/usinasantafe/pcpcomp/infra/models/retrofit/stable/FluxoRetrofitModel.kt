package br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo

data class FluxoRetrofitModel(
    val idFluxo: Int,
    val descrFluxo: String,
)

fun FluxoRetrofitModel.retrofitModelToEntity(): Fluxo {
    return with(this) {
        Fluxo (
            idFluxo = this.idFluxo,
            descrFluxo = this.descrFluxo,
        )
    }
}