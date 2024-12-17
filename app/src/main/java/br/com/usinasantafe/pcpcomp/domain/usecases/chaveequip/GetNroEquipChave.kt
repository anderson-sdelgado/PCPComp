package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface GetNroEquipChave {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetNroEquipChave(): GetNroEquipChave {

    override suspend fun invoke(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

}