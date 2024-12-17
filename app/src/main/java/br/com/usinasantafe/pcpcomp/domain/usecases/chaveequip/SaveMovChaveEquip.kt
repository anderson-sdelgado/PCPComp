package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.utils.TypeMovKey

interface SaveMovChaveEquip {
    suspend operator fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean>
}

class ISaveMovChaveEquip(): SaveMovChaveEquip {

    override suspend fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}