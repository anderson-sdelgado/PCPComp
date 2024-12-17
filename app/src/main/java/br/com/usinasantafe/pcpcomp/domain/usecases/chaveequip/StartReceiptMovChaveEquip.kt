package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface StartReceiptMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartReceiptMovChaveEquip(): StartReceiptMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}