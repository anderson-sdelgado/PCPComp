package br.com.usinasantafe.pcpcomp.presenter.chave.model

data class ControleChaveModel(
    val id: Int,
    val dthr: String,
    val tipoMov: String? = null,
    val chave: String,
    val colab: String,
)
