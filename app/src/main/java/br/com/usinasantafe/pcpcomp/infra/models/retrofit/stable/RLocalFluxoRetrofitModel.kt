package br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo

data class RLocalFluxoRetrofitModel(
    val idRLocalFluxo: Int,
    val idLocal: Int,
    val idFluxo: Int,
)

fun RLocalFluxoRetrofitModel.retrofitModelToEntity(): RLocalFluxo {
    return with(this) {
        RLocalFluxo(
            idRLocalFluxo = this.idRLocalFluxo,
            idFluxo = this.idFluxo,
            idLocal = this.idLocal,
        )
    }
}