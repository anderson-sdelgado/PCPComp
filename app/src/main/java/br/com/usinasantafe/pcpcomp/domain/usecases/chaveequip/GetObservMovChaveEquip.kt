package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface GetObservMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<String?>
}

class IGetObservMovChaveEquip(): GetObservMovChaveEquip {

    override suspend fun invoke(id: Int): Result<String?> {
        TODO("Not yet implemented")
    }

}