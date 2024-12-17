package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

interface GetNomeColabChaveEquip {
    suspend operator fun invoke(matric: String): Result<String>
}

class IGetNomeColabChaveEquip(): GetNomeColabChaveEquip {

    override suspend fun invoke(matric: String): Result<String> {
        TODO("Not yet implemented")
    }

}