package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface GetMatricColabMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetMatricColabMovChaveEquip(): GetMatricColabMovChaveEquip {

    override suspend fun invoke(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

}