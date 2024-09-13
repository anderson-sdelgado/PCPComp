package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

interface StartMovEquipVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class StartMovEquipVisitTercImpl(): StartMovEquipVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}