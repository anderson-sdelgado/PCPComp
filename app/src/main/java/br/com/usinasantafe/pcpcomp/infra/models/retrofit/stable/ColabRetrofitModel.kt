package br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab

data class ColabRetrofitModel(
    val matricColab: Int,
    val nomeColab: String,
)

fun ColabRetrofitModel.retrofitModelToEntity(): Colab {
    return with(this) {
        Colab(
            matricColab = this.matricColab,
            nomeColab = this.nomeColab,
        )
    }
}