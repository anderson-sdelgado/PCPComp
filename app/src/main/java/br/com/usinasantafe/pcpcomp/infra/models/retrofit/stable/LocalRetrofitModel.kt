package br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local

data class LocalRetrofitModel (
    val idLocal: Int,
    val descrLocal: String,
)

fun LocalRetrofitModel.retrofitModelToEntity(): Local {
    return with(this){
        Local(
            idLocal = this.idLocal,
            descrLocal = this.descrLocal,
        )
    }
}