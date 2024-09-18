package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

interface StartMovEquipResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class StartMovEquipResidenciaImpl(): StartMovEquipResidencia {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}