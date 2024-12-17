package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface CloseMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ICloseMovChaveEquip(): CloseMovChaveEquip {

    override suspend fun invoke(id: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

}