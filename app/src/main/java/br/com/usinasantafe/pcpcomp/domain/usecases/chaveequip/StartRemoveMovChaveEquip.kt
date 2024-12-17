package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface StartRemoveMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartRemoveMovChaveEquip(): StartRemoveMovChaveEquip {

    override suspend fun invoke(id: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

}