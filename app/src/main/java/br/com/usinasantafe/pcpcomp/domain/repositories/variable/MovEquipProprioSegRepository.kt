package br.com.usinasantafe.pcpcomp.domain.repositories.variable

interface MovEquipProprioSegRepository {
    suspend fun clear(): Result<Boolean>
}