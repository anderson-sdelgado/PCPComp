package br.com.usinasantafe.pcpcomp.domain.repositories.variable

interface MovEquipProprioPassagRepository {
    suspend fun clear(): Result<Boolean>
}