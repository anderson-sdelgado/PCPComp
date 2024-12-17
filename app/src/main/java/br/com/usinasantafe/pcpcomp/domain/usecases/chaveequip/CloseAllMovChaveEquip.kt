package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface CloseAllMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovChaveEquip(): CloseAllMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}